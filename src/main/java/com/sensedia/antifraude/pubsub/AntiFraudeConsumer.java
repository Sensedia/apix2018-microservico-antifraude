package com.sensedia.antifraude.pubsub;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubOperations;
import org.springframework.cloud.gcp.pubsub.integration.AckMode;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.gson.Gson;
import com.sensedia.antifraude.domain.SolicitacaoPagamento;
import com.sensedia.antifraude.domain.enums.StatusEnum;
import com.sensedia.antifraude.dto.topico.input.SolicitacaoPagamentoInput;
import com.sensedia.antifraude.dto.topico.output.SolicitacaoPagamentoDetalheOutput;
import com.sensedia.antifraude.dto.topico.output.SolicitacaoPagamentoOutput;
import com.sensedia.antifraude.service.IAntiFraudeService;

@Component
public class AntiFraudeConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(AntiFraudeConsumer.class);

	private static final String CHANNEL = "antifraudeInputChannel";

	@Value("${subscription.name}")
	private String subscriptionName;

	@Autowired
	private IAntiFraudeService pagamentoService;

	@Autowired
	private AntiFraudeProducer.AntiFraudeGateway antiFraudeGateway;

	@Autowired
	private Gson gson;

	@Bean
	public PubSubInboundChannelAdapter messageChannelAdapter(@Qualifier(CHANNEL) MessageChannel inputChannel,
			PubSubOperations pubSubTemplate) {

		PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, subscriptionName);
		adapter.setOutputChannel(inputChannel);
		adapter.setAckMode(AckMode.MANUAL);
		return adapter;

	}

	@Bean
    @ServiceActivator(inputChannel = CHANNEL)
    public MessageHandler messageReceiver() {
        return message -> {
            
        	LOGGER.debug("[Solicitacao: {}] Mensagem recebida: {}", null,  message.getPayload());
            AckReplyConsumer consumer = (AckReplyConsumer) message.getHeaders().get(GcpPubSubHeaders.ACKNOWLEDGEMENT);

            try {
            	
            	SolicitacaoPagamentoInput input = gson.fromJson(message.getPayload().toString(), SolicitacaoPagamentoInput.class);

            	SolicitacaoPagamento solicitacao = input.toDomain();
            	
                pagamentoService.processar(solicitacao);
                
                antiFraudeGateway.sendToPubsub(message.getPayload().toString());
                
                consumer.ack();
                
			} catch (Exception e) {
                consumer.nack();
				LOGGER.error("Erro processando mensagem", e);
            }
            
        };
    }

}
