-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 26/05/2026 às 23:13
-- Versão do servidor: 10.4.32-MariaDB
-- Versão do PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `gestao_hotel`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `cliente`
--

CREATE TABLE `cliente` (
  `id` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `data_nascimento` date NOT NULL,
  `fk_tipo_cliente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `cliente`
--

INSERT INTO `cliente` (`id`, `nome`, `data_nascimento`, `fk_tipo_cliente`) VALUES
(1, 'Jessé Jacinto', '1997-05-10', 1),
(2, 'Jacinto Leite', '1995-10-12', 2),
(3, 'TeMelo Rego', '1992-10-12', 3);

-- --------------------------------------------------------

--
-- Estrutura para tabela `estado_reserva`
--

CREATE TABLE `estado_reserva` (
  `id` int(11) NOT NULL,
  `estado` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `estado_reserva`
--

INSERT INTO `estado_reserva` (`id`, `estado`) VALUES
(1, 'Pendente'),
(2, 'Paga'),
(3, 'Concluída');

-- --------------------------------------------------------

--
-- Estrutura para tabela `quarto`
--

CREATE TABLE `quarto` (
  `id` int(11) NOT NULL,
  `preco_noite` decimal(10,2) NOT NULL,
  `fk_tipo_quarto` int(11) NOT NULL,
  `num_quartos` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `quarto`
--

INSERT INTO `quarto` (`id`, `preco_noite`, `fk_tipo_quarto`, `num_quartos`) VALUES
(1, 100.00, 1, 1),
(2, 200.00, 2, 2),
(3, 300.00, 3, 3),
(4, 300.00, 3, 3);

-- --------------------------------------------------------

--
-- Estrutura para tabela `reserva`
--

CREATE TABLE `reserva` (
  `id` int(11) NOT NULL,
  `data_reserva` date NOT NULL DEFAULT current_timestamp(),
  `data_checkin` date NOT NULL,
  `data_checkout` date NOT NULL,
  `fk_estado_reserva` int(11) NOT NULL,
  `fk_id_cliente` int(11) NOT NULL,
  `fk_id_quarto` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `tipo_cliente`
--

CREATE TABLE `tipo_cliente` (
  `id` int(11) NOT NULL,
  `tipo` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `tipo_cliente`
--

INSERT INTO `tipo_cliente` (`id`, `tipo`) VALUES
(1, 'Normal'),
(2, 'Vip'),
(3, 'Especial');

-- --------------------------------------------------------

--
-- Estrutura para tabela `tipo_quarto`
--

CREATE TABLE `tipo_quarto` (
  `id` int(11) NOT NULL,
  `tamanho` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `tipo_quarto`
--

INSERT INTO `tipo_quarto` (`id`, `tamanho`) VALUES
(1, 'solteiro'),
(2, 'casal'),
(3, 'suíte');

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_tipo_cliente` (`fk_tipo_cliente`);

--
-- Índices de tabela `estado_reserva`
--
ALTER TABLE `estado_reserva`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `quarto`
--
ALTER TABLE `quarto`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_tipo_quarto` (`fk_tipo_quarto`);

--
-- Índices de tabela `reserva`
--
ALTER TABLE `reserva`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_id_cliente` (`fk_id_cliente`),
  ADD KEY `fk_id_quarto` (`fk_id_quarto`),
  ADD KEY `fk_estado_reserva` (`fk_estado_reserva`);

--
-- Índices de tabela `tipo_cliente`
--
ALTER TABLE `tipo_cliente`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `tipo_quarto`
--
ALTER TABLE `tipo_quarto`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `estado_reserva`
--
ALTER TABLE `estado_reserva`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `quarto`
--
ALTER TABLE `quarto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de tabela `reserva`
--
ALTER TABLE `reserva`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `tipo_cliente`
--
ALTER TABLE `tipo_cliente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `tipo_quarto`
--
ALTER TABLE `tipo_quarto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`fk_tipo_cliente`) REFERENCES `tipo_cliente` (`id`);

--
-- Restrições para tabelas `quarto`
--
ALTER TABLE `quarto`
  ADD CONSTRAINT `quarto_ibfk_1` FOREIGN KEY (`fk_tipo_quarto`) REFERENCES `tipo_quarto` (`id`);

--
-- Restrições para tabelas `reserva`
--
ALTER TABLE `reserva`
  ADD CONSTRAINT `reserva_ibfk_1` FOREIGN KEY (`fk_id_cliente`) REFERENCES `cliente` (`id`),
  ADD CONSTRAINT `reserva_ibfk_2` FOREIGN KEY (`fk_id_quarto`) REFERENCES `quarto` (`id`),
  ADD CONSTRAINT `reserva_ibfk_3` FOREIGN KEY (`fk_estado_reserva`) REFERENCES `estado_reserva` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
