# Criar o database chamado db_cliente
create database if not exists db_cliente;

# Entrar no database db_cliente
use db_cliente;

# Remover a tabela para recriá-la
drop table if exists cliente;

# Criar a tabela de tb_alunos
create table cliente (clienteid integer, 
                      nome      varchar(100), 
                      cpf       varchar(11), 
                      constraint pk_cliente primary key (clienteid));

# Listar a tabela criada
show tables;
