package be.charleshornick.supra.lib.cqs.spring;

import org.springframework.transaction.support.TransactionTemplate;

public record WriteTransaction(TransactionTemplate template) {
}
