package be.charleshornick.supra.lib.cqs.spring;

import org.springframework.transaction.support.TransactionTemplate;

public record ReadOnlyTransaction(TransactionTemplate template) {
}
