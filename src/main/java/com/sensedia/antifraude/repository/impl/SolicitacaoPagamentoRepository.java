package com.sensedia.antifraude.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.DoubleValue;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.LatLngValue;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.StructuredQuery.CompositeFilter;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;
import com.google.cloud.datastore.TimestampValue;
import com.google.cloud.datastore.ValueBuilder;
import com.sensedia.antifraude.domain.SolicitacaoPagamento;
import com.sensedia.antifraude.domain.enums.StatusEnum;
import com.sensedia.antifraude.repository.ISolicitacaoPagamentoRepository;

@Component
public class SolicitacaoPagamentoRepository implements ISolicitacaoPagamentoRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(SolicitacaoPagamentoRepository.class);

	@Value("${datastore.namespace}")
	private String datastoreNamespace;

	@Value("${datastore.kind}")
	private String datastoreKind;

	private Datastore datastore;

	private KeyFactory keyFactory;

	@PostConstruct
	public void setup() {

		datastore = DatastoreOptions.getDefaultInstance().getService();
		keyFactory = datastore.newKeyFactory().setNamespace(datastoreNamespace).setKind(datastoreKind);
	}

	@Override
	public Long salvarSolicitacao(final SolicitacaoPagamento solicitacaoPagamento, final StatusEnum status) {

		Key key = datastore.allocateId(keyFactory.newKey());

		Entity entity = Entity.newBuilder(key)
				.set(SolicitacaoPagamento.VALOR, build(DoubleValue.newBuilder(solicitacaoPagamento.getValor())))
				.set(SolicitacaoPagamento.STATUS, build(StringValue.newBuilder(status.name())))
				.set(SolicitacaoPagamento.DATA_CRIACAO,
						build(TimestampValue.newBuilder(solicitacaoPagamento.getDataCriacao())))
				.set(SolicitacaoPagamento.GEOLOCALIZACAO,
						build(LatLngValue.newBuilder(solicitacaoPagamento.getGeolocalizacao())))
				.set(SolicitacaoPagamento.CARTAO_NUMERO,
						build(StringValue.newBuilder(solicitacaoPagamento.getNumeroCartao())))
				.set(SolicitacaoPagamento.CARTAO_NOME,
						build(StringValue.newBuilder(solicitacaoPagamento.getNomeCartao())))
				.set(SolicitacaoPagamento.CARTAO_VALIDADE,
						build(StringValue.newBuilder(solicitacaoPagamento.getValidadeCartao())))
				.set(SolicitacaoPagamento.CARTAO_CVV,
						build(StringValue.newBuilder(solicitacaoPagamento.getCvvCartao())))
				.set(SolicitacaoPagamento.CODIGO_PAGAMENTO,
						build(StringValue.newBuilder(solicitacaoPagamento.getCodigoPagamento())))
				.set(SolicitacaoPagamento.USUARIO_CPF,
						build(StringValue.newBuilder(solicitacaoPagamento.getUsuarioCPF())))
				.set(SolicitacaoPagamento.USUARIO_NOME,
						build(StringValue.newBuilder(solicitacaoPagamento.getUsuarioNome())))
				.set(SolicitacaoPagamento.USUARIO_TELEFONE,
						build(StringValue.newBuilder(solicitacaoPagamento.getUsuarioTelefone())))
				.set(SolicitacaoPagamento.USUARIO_EMAIL, solicitacaoPagamento.getUsuarioEmail()).build();

		datastore.put(entity);

		return entity.getKey().getId();

	}

	@Override
	public SolicitacaoPagamento retornarUltimoRegistroDaSolicitacaoPagamento(final String numeroCartao) {

		try {

			Query<Entity> query = Query.newEntityQueryBuilder().setNamespace(datastoreNamespace).setKind(datastoreKind)
					.setOrderBy(OrderBy.desc(SolicitacaoPagamento.DATA_CRIACAO))
					 .setFilter(CompositeFilter.and(
					 PropertyFilter.eq(SolicitacaoPagamento.CARTAO_NUMERO, numeroCartao),
					 PropertyFilter.eq(SolicitacaoPagamento.STATUS, StatusEnum.PROCESSADO_SUCESSO.name())))
					.setLimit(1).build();

			QueryResults<Entity> run = datastore.run(query);
			Entity entity = run.next();
			return preencherSolicitacaoPagamento(entity);

		} catch (Exception e) {
			LOGGER.info("[Solicitacao: {}] Ocorreu um erro durante a consulta do cartao {}: {}", null, numeroCartao,
					e.getMessage());
			return null;
		}

	}

	public SolicitacaoPagamento buscarPorId(Long id) {

		Entity solicitacao = datastore.get(keyFactory.newKey(id));

		if (Objects.nonNull(solicitacao)) {

			return SolicitacaoPagamento.Builder.newBuilder().id(solicitacao.getKey().getId())
					.valor(solicitacao.getDouble(SolicitacaoPagamento.VALOR))
					.status(StatusEnum.valueOf(solicitacao.getString(SolicitacaoPagamento.STATUS)))
					.dataCriacao(solicitacao.getTimestamp(SolicitacaoPagamento.DATA_CRIACAO))
					.geolocalizacao(solicitacao.getLatLng(SolicitacaoPagamento.GEOLOCALIZACAO))
					.numeroCartao(solicitacao.getString(SolicitacaoPagamento.CARTAO_NUMERO))
					.nomeCartao(solicitacao.getString(SolicitacaoPagamento.CARTAO_NOME))
					.validadeCartao(solicitacao.getString(SolicitacaoPagamento.CARTAO_VALIDADE))
					.cvvCartao(solicitacao.getString(SolicitacaoPagamento.CARTAO_CVV))
					.codigoPagamento(solicitacao.getString(SolicitacaoPagamento.CODIGO_PAGAMENTO))
					.usuarioCPF(solicitacao.getString(SolicitacaoPagamento.USUARIO_CPF))
					.usuarioNome(solicitacao.getString(SolicitacaoPagamento.USUARIO_NOME))
					.usuarioTelefone(solicitacao.getString(SolicitacaoPagamento.USUARIO_TELEFONE)).build();

		}

		return null;

	}

	@Override
	public Double retornarValorMediaPagamento(final String numeroCartao) {

		Query<Entity> query = Query.newEntityQueryBuilder().setNamespace(datastoreNamespace).setKind(datastoreKind)
				.setOrderBy(OrderBy.desc(SolicitacaoPagamento.DATA_CRIACAO))
				 .setFilter(CompositeFilter.and(
				 PropertyFilter.eq(SolicitacaoPagamento.CARTAO_NUMERO, numeroCartao),
				 PropertyFilter.eq(SolicitacaoPagamento.STATUS, StatusEnum.PROCESSADO_SUCESSO.name())))
				.build();

		QueryResults<Entity> result = datastore.run(query);

		List<Double> valores = new ArrayList<>();
		while (result.hasNext()) {
			Entity entity = result.next();
			valores.add(entity.getDouble(SolicitacaoPagamento.VALOR));
		}

		return valores.stream().mapToDouble(c -> c).average().orElseGet(() -> Double.valueOf(0));
	}

	private SolicitacaoPagamento preencherSolicitacaoPagamento(Entity entity) {
		return SolicitacaoPagamento.Builder.newBuilder().id(entity.getLong(SolicitacaoPagamento.ID))
				.valor(entity.getDouble(SolicitacaoPagamento.VALOR))
				.status(StatusEnum.valueOf(entity.getString(SolicitacaoPagamento.STATUS)))
				.dataCriacao(entity.getTimestamp(SolicitacaoPagamento.DATA_CRIACAO))
				.geolocalizacao(entity.getLatLng(SolicitacaoPagamento.GEOLOCALIZACAO))
				.numeroCartao(entity.getString(SolicitacaoPagamento.CARTAO_NUMERO))
				.nomeCartao(entity.getString(SolicitacaoPagamento.CARTAO_NOME))
				.validadeCartao(entity.getString(SolicitacaoPagamento.CARTAO_VALIDADE))
				.cvvCartao(entity.getString(SolicitacaoPagamento.CARTAO_CVV))
				.codigoPagamento(entity.getString(SolicitacaoPagamento.CODIGO_PAGAMENTO))
				.usuarioCPF(entity.getString(SolicitacaoPagamento.USUARIO_CPF))
				.usuarioNome(entity.getString(SolicitacaoPagamento.USUARIO_NOME))
				.usuarioTelefone(entity.getString(SolicitacaoPagamento.USUARIO_TELEFONE))
				.usuarioEmail(entity.getString(SolicitacaoPagamento.USUARIO_EMAIL)).build();
	}

	private static com.google.cloud.datastore.Value<?> build(ValueBuilder<?, ?, ?> valueBuilder) {
		return valueBuilder.build();
	}

}
