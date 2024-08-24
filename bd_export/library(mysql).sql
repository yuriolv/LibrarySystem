-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema library
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema library
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `library` ;
USE `library` ;

-- -----------------------------------------------------
-- Table `library`.`Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`Usuario` (
  `matricula` VARCHAR(10) NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `senha` VARCHAR(255) NOT NULL,
  `tipo` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`matricula`),
  UNIQUE INDEX `Matricula_UNIQUE` (`matricula` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `library`.`Livros`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`Livros` (
  `id` INT NOT NULL,
  `autor` VARCHAR(255) NOT NULL,
  `titulo` VARCHAR(255) NOT NULL,
  `assunto` VARCHAR(255) NOT NULL,
  `qtd_estoque` INT ZEROFILL NOT NULL,
  `capa` VARCHAR(255) NOT NULL,
  `colecao` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `library`.`Comentario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`Comentario` (
  `id` INT NOT NULL,
  `id_livro` INT NOT NULL,
  `comentario` TINYTEXT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `id_livro_idx` (`id_livro` ASC) VISIBLE,
  CONSTRAINT `fk_id_livro2`
    FOREIGN KEY (`id_livro`)
    REFERENCES `library`.`Livros` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `library`.`Telefone`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`Telefone` (
  `id` INT NOT NULL,
  `numero` VARCHAR(15) NOT NULL,
  `matricula` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `matricula_idx` (`matricula` ASC) VISIBLE,
  CONSTRAINT `fk_matricula2`
    FOREIGN KEY (`matricula`)
    REFERENCES `library`.`Usuario` (`matricula`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `library`.`Aluguel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`Aluguel` (
  `id` INT NOT NULL,
  `matricula` VARCHAR(10) NOT NULL,
  `id_livro` INT NOT NULL,
  `data_aluguel` DATE NOT NULL,
  `data_devolução` DATE NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `id_livro_idx` (`id_livro` ASC) VISIBLE,
  INDEX `matricula_idx` (`matricula` ASC) VISIBLE,
  CONSTRAINT `fk_id_livro1`
    FOREIGN KEY (`id_livro`)
    REFERENCES `library`.`Livros` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_matricula1`
    FOREIGN KEY (`matricula`)
    REFERENCES `library`.`Usuario` (`matricula`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
