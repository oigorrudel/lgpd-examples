package br.xksoberbado.rabbitmqexample.service;

public interface IKeyService {

    byte[] encrypt(byte[] bytes);

    byte[] decrypt(byte[] bytes);
}
