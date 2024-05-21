DROP DATABASE IF EXISTS PRACTICA;
CREATE SCHEMA IF NOT EXISTS PRACTICA DEFAULT CHARACTER SET latin1 ;
USE PRACTICA;

DROP TABLE IF EXISTS PRACTICA.EM_EMPLEADOS;
CREATE TABLE PRACTICA.EM_EMPLEADOS
  (
   ID_EMPLEADO 				INT(7) PRIMARY KEY NOT NULL AUTO_INCREMENT,	
   TX_NIF 					VARCHAR(9) UNIQUE,									
   TX_NOMBRE 				VARCHAR(30) NOT NULL,			
   TX_APELLIDO1 			VARCHAR(40) NOT NULL,			
   TX_APELLIDO2 			VARCHAR(40) NOT NULL,			
   F_NACIMIENTO				DATE  NOT NULL,								
   N_TELEFONO1 				VARCHAR(12) NOT NULL,						
   N_TELEFONO2 				VARCHAR(12) NOT NULL,						
   TX_EMAIL 				VARCHAR(40) NOT NULL,						
   F_ALTA 					DATE  NOT NULL,		
   F_BAJA					DATE,
   CX_EDOCIVIL 				CHAR(1) NOT NULL,							
   B_SERVMILITAR 			CHAR(1) NOT NULL	                      
);

DROP TABLE IF EXISTS PRACTICA.PR_PROYECTOS;
CREATE TABLE PRACTICA.PR_PROYECTOS
(  
   ID_PROYECTO 				INT(5) PRIMARY KEY NOT NULL AUTO_INCREMENT,
   TX_DESCRIPCIÓN 			VARCHAR(125) NOT NULL,
   F_INICIO 				DATE NOT NULL,
   F_FIN 					DATE,
   F_BAJA					DATE,
   TX_LUGAR 				VARCHAR(30),
   TX_OBSERVACIONES 		VARCHAR(300)
);

DROP TABLE IF EXISTS PRACTICA.PR_EMPLEADOS_PROYECTO;
CREATE TABLE PRACTICA.PR_EMPLEADOS_PROYECTO
(
   ID_PROYECTO 				INT(5) NOT NULL,
   ID_EMPLEADO 				INT(7) NOT NULL,
   F_ALTA					DATE,
   CONSTRAINT PK_PR_EMPLEADOS_PROYECTO PRIMARY KEY (ID_PROYECTO, ID_EMPLEADO)
);

ALTER TABLE PRACTICA.EM_EMPLEADOS ADD CONSTRAINT CHK_EMAIL CHECK (TX_EMAIL REGEXP '^[a-zA-Z0-9][a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$');
ALTER TABLE PRACTICA.EM_EMPLEADOS ADD CONSTRAINT CHK_ECIVIL CHECK (UPPER(CX_EDOCIVIL) IN ('S', 'C'));
ALTER TABLE PRACTICA.EM_EMPLEADOS ADD CONSTRAINT CHK_SERVMIL CHECK (UPPER(B_SERVMILITAR) IN ('S', 'N'));
ALTER TABLE PRACTICA.PR_EMPLEADOS_PROYECTO ADD CONSTRAINT FK_PR_EMPLEADOS_PROYECTO_EM_EMPLEADOS_01 FOREIGN KEY (ID_EMPLEADO) REFERENCES PRACTICA.EM_EMPLEADOS (ID_EMPLEADO);
ALTER TABLE PRACTICA.PR_EMPLEADOS_PROYECTO ADD CONSTRAINT FK_PR_EMPLEADOS_PROYECTO_PR_PROYECTOS_02 FOREIGN KEY (ID_PROYECTO) REFERENCES PRACTICA.PR_PROYECTOS (ID_PROYECTO);