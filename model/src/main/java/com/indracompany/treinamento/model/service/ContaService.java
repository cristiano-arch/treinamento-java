package com.indracompany.treinamento.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indracompany.treinamento.exception.AplicacaoException;
import com.indracompany.treinamento.exception.ExceptionValidacoes;
import com.indracompany.treinamento.model.entity.Conta;
import com.indracompany.treinamento.model.repository.ContaRepository;

@Service
public class ContaService extends GenericCrudService<Conta, Long, ContaRepository>{

//	"/consultar-saldo/{agencia}/{conta}"
//	"/consultar-contas-cliente/{cpf}"
//	"/deposito"
//	"/saque"
	
	@Autowired
	private ContaRepository contaRepository;
	
	public Double consultarSaldo(String agencia, String numero) {
		return encontrarConta(agencia, numero).getSaldo();
	}
	
	public Conta sacar(String agencia, String numero, Double valor) {
		
		Conta conta = encontrarConta(agencia, numero);
		
		if (conta.getSaldo() > valor) {
			conta.setSaldo(conta.getSaldo()-valor);
			return contaRepository.save(conta);
		} else {
			throw new AplicacaoException(ExceptionValidacoes.ERRO_SALDO_INEXISTENTE);
		}
	}
	
	public List<Conta> consultarContaCliente(String cpf) {
		return contaRepository.findByClienteCpf(cpf);
	}
	
	public Conta encontrarConta(String agencia, String numero) {
		Conta conta = contaRepository.findByAgenciaAndNumero(agencia, numero);
		
		if (conta != null) {
			return conta;
		} else {
			throw new AplicacaoException(ExceptionValidacoes.ERRO_CONTA_INVALIDA);
		}
	}
}
