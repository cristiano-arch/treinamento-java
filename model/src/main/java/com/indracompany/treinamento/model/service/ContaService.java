package com.indracompany.treinamento.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		
		Conta conta = contaRepository.findByAgenciaAndNumero(agencia, numero);
		return conta.getSaldo();
	}
	
	public Conta sacar(String agencia, String numero, Double valor) {
		
		Conta conta = contaRepository.findByAgenciaAndNumero(agencia, numero);
		conta.setSaldo(conta.getSaldo()-valor);
		
		return contaRepository.save(conta);
	}
	
	public List<Conta> consultarContaCliente(String cpf) {
		return contaRepository.findByClienteCpf(cpf);
	}
}
