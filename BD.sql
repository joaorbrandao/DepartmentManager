#---------- DESPORTIVOBD ----------
	#DROP
    drop database if exists desportivobd;
    #CREATE
    create database if not exists desportivobd;
	#SHOW TABLES
    show tables from desportivobd;





#---------- UTILIZADORES ----------
	#DROP
	drop table if exists desportivobd.utilizadores;
    #CREATE
    create table desportivobd.utilizadores(
		util_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
		util_email VARCHAR(45) NOT NULL, 
		util_nome VARCHAR(45) NOT NULL, 
		util_data_nascimento VARCHAR(10) NOT NULL, 
		util_sexo VARCHAR(1) NOT NULL, 
		util_telefone VARCHAR(9) NOT NULL, 
		util_cartao_cidadao VARCHAR(8) NOT NULL, 
		mod_id INT NOT NULL, 
		ativo boolean NOT NULL DEFAULT 1,
        FOREIGN KEY (mod_id) REFERENCES desportivobd.modalidades(mod_id));
	#INSERT
	insert into desportivobd.utilizadores(
		util_email, 
        util_nome, 
        util_data_nascimento, 
        util_sexo, 
        util_telefone, 
        util_cartao_cidadao,
        mod_id)
	values( 
		"admin@isep.ipp.pt",
        "admin admin", 
        "01/01/2000", 
        "M", 
        "235251755", 
        "87654321",
        2);
	#DELETE
    delete from desportivobd.utilizadores where util_id=1;
    #RESET AUTO_INCREMENT
    ALTER TABLE desportivobd.utilizadores AUTO_INCREMENT=1;
    #SELECT
    SELECT * FROM desportivobd.utilizadores;
	#UPDATE
    update desportivobd.utilizadores set util_nome="admin admin" where util_id=7;




#---------- AUTENTICACAO ----------
	#DROP
    drop table if exists desportivobd.autenticacao;
    #CREATE
    create table desportivobd.autenticacao(
		aut_id int not null auto_increment primary key,
		util_id int not null,
		aut_password varchar(45) not null,
		aut_nivel boolean not null default 0,
		ativo boolean not null default 1,
        foreign key (util_id) references desportivobd.utilizadores(util_id));
	#INSERT
	insert into desportivobd.autenticacao(
		util_id,
		aut_password, 
        aut_nivel)
	values(
		1,
		"admin",
		1);
	#DELETE
    delete from desportivobd.autenticacao where aut_id=1;
    #SELECT
    select * from desportivobd.autenticacao;
	#UPDATE
    update desportivobd.autenticacao set aut_nivel=1 where util_id=7;




#---------- MODALIDADES ----------
	#DROP
	drop table if exists desportivobd.modalidades;
    #CREATE
    create table desportivobd.modalidades(
		mod_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
		mod_nome VARCHAR(45) NOT NULL, 
		ativo boolean NOT NULL default 1);
	#INSERT
    insert into desportivobd.modalidades(
		mod_nome)
	values(
		"Natação");
	#DELETE
    delete from desportivobd.modalidades where mod_id=4;
    #SELECT
    select * from desportivobd.modalidades;
    SELECT mod_nome FROM modalidades WHERE ativo=1;




#---------- PROVAS ----------
	#DROP 
    drop table if exists desportivobd.provas;
    #CREATE 
    create table desportivobd.provas(
		prova_id int not null auto_increment primary key,
		mod_id int not null,
		prova_nome varchar(45) not null,
		prova_hora varchar(5) not null,
		prova_data varchar(10) not null,
		prova_local varchar(45) not null,
		prova_sexo varchar(1) not null,
		ativo boolean not null default 1,
		foreign key (mod_id) references desportivobd.modalidades(mod_id));
	#INSERT
    insert into desportivobd.provas(
		mod_id,
        prova_nome,
        prova_hora,
        prova_data,
        prova_local,
        prova_sexo) 
    values(
		4,
        "CNU - Natação Piscina Curta m",
        "14h00",
        "06/12/2015",
        "Matosinhos",
        "M");
    #SELECT
    select * from desportivobd.provas;





#---------- UTILIZADORES_MODALIDADES ----------
	#DROP
	drop table if exists desportivobd.utilizadores_modalidades;
    #CREATE
    create table desportivobd.utilizadores_modalidades(
		util_id int not null,
		mod_id int not null,
        timestamp timestamp not null,
		estado int NOT NULL default 2,
        foreign key (util_id) references desportivobd.utilizadores(util_id),
        foreign key (mod_id) references desportivobd.modalidades(mod_id));
	#INSERT
    insert into desportivobd.utilizadores_modalidades(
		util_id,
        mod_id,
        estado) 
	values (
		1,
        1,
        2);
	#SELECT
    select * from desportivobd.utilizadores_modalidades;
    #UPDATE
    update desportivobd.utilizadores_modalidades set estado=0 where util_id=4 and mod_id=3;
	#DELETE
    delete from desportivobd.utilizadores_modalidades where util_id=4 and mod_id=1 and estado=1;




#---------- UTILIZADORES_PROVAS ----------
	#DROP
	drop table if exists desportivobd.utilizadores_provas;
    #CREATE
    create table desportivobd.utilizadores_provas(
		util_id int not null, 
		prova_id int not null,
        timestamp timestamp not null,
		estado int NOT NULL default 1,
        foreign key (util_id) references desportivobd.utilizadores(util_id),
        foreign key (prova_id) references desportivobd.provas(prova_id));
	#INSERT
    insert into desportivobd.utilizadores_provas(
		util_id,
        prova_id,
        estado)
    values(
		4,
        2,
        2);
	#SELECT
    select * from desportivobd.utilizadores_provas;
    select * from desportivobd.utilizadores_provas where util_id=4;
	#UPDATE
    update desportivobd.utilizadores_provas set estado=0 where util_id=4 and prova_id=1;
	#DELETE
    delete from desportivobd.utilizadores_provas where util_id=4 and estado=1;


