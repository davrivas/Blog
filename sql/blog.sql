-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 30, 2019 at 12:27 AM
-- Server version: 10.1.35-MariaDB
-- PHP Version: 7.2.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `blog`
--

-- --------------------------------------------------------

--
-- Table structure for table `categoria_publicacion`
--

CREATE TABLE `categoria_publicacion` (
  `id` int(11) NOT NULL,
  `nombre` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `nombre_plural` varchar(25) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Dumping data for table `categoria_publicacion`
--

INSERT INTO `categoria_publicacion` (`id`, `nombre`, `nombre_plural`) VALUES
(1, 'Noticia', 'Noticias'),
(2, 'Evento', 'Eventos'),
(3, 'Discusión', 'Discusiones');

-- --------------------------------------------------------

--
-- Table structure for table `comentario`
--

CREATE TABLE `comentario` (
  `id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  `publicacion_id` int(11) NOT NULL,
  `comentario` varchar(1000) COLLATE utf8_spanish_ci NOT NULL,
  `fecha_publicacion` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Dumping data for table `comentario`
--

INSERT INTO `comentario` (`id`, `usuario_id`, `publicacion_id`, `comentario`, `fecha_publicacion`) VALUES
(1, 1, 3, 'Que buen articulo!', '2019-09-25 20:39:40'),
(2, 2, 3, 'Me gustó este artículo, recomendadísimo.', '2019-09-25 20:53:10'),
(3, 6, 3, 'Me gusta esta página, quiero más artículos.', '2019-09-28 10:02:59'),
(4, 5, 3, 'I don\'t know spanish, but this is cool!', '2019-09-28 10:11:10'),
(5, 2, 4, 'Estaré en primera fila.', '2019-09-28 10:20:50'),
(6, 1, 5, 'Este es un buen artículo.', '2019-09-28 17:22:19');

-- --------------------------------------------------------

--
-- Table structure for table `publicacion`
--

CREATE TABLE `publicacion` (
  `id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  `categoria_publicacion_id` int(11) NOT NULL,
  `titulo` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `contenido` text COLLATE utf8_spanish_ci NOT NULL,
  `fecha_publicacion` datetime NOT NULL,
  `estado` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Dumping data for table `publicacion`
--

INSERT INTO `publicacion` (`id`, `usuario_id`, `categoria_publicacion_id`, `titulo`, `contenido`, `fecha_publicacion`, `estado`) VALUES
(3, 1, 1, 'Primera publicación', '<p>Este un ejemplo de publicación.</p><p><img src=\"https://i.imgur.com/Pt6qsYF.jpg\" alt=\"roco\"><br></p><p>Este es otro <strong>párrafo,</strong> miremos si se <em>edita</em>.</p>', '2019-09-21 18:21:23', 1),
(4, 1, 2, 'Concierto cristiano', '<p>Este 1 de octubre habrá un concierto de beneficiencia por los niños.</p><p>Entradas en primera fila <a href=\"tel:+5714656745\" title=\"Llamar\">465 67 45</a>.</p>', '2019-09-21 20:13:46', 1),
(5, 1, 3, 'Publicación de prueba', '<p style=\"text-align: left;\">Hola <strong>esta </strong>es una <em>publicación </em>de prueba</p><p style=\"text-align: left;\"><img src=\"https://i.imgur.com/9Zr3IDM.jpg\" alt=\"imagen\"><br></p><p style=\"text-align: left;\">Visite nuestro sitio <a href=\"http://www.google.com.co\" title=\"nuestro sitio\">aquí</a></p><h4 style=\"text-align: left;\">Este es un nuevo párrafo</h4>', '2019-09-28 17:21:27', 0);

-- --------------------------------------------------------

--
-- Table structure for table `rol`
--

CREATE TABLE `rol` (
  `id` int(11) NOT NULL,
  `nombre` varchar(25) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Dumping data for table `rol`
--

INSERT INTO `rol` (`id`, `nombre`) VALUES
(1, 'Administrador'),
(2, 'Visitante');

-- --------------------------------------------------------

--
-- Table structure for table `usuario`
--

CREATE TABLE `usuario` (
  `id` int(11) NOT NULL,
  `rol_id` int(11) NOT NULL,
  `nombres` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `apellidos` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `correo` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `clave` varchar(16) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Dumping data for table `usuario`
--

INSERT INTO `usuario` (`id`, `rol_id`, `nombres`, `apellidos`, `correo`, `clave`) VALUES
(1, 1, 'Andres', 'Bustos', 'andresbustos@gmail.com', 'andres'),
(2, 2, 'David', 'Rivas', 'd.rivas95@hotmail.com', 'david'),
(3, 1, 'John', 'Doe', 'johndoe@mail.com', 'john'),
(5, 2, 'Jane', 'Doe', 'janedoe@mail.com', 'jane'),
(6, 2, 'Juan', 'Perez', 'juanperez@gmail.com', 'juan');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categoria_publicacion`
--
ALTER TABLE `categoria_publicacion`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `comentario`
--
ALTER TABLE `comentario`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_comentario_usuario1_idx` (`usuario_id`),
  ADD KEY `fk_comentario_publicacion1_idx` (`publicacion_id`);

--
-- Indexes for table `publicacion`
--
ALTER TABLE `publicacion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_publicaciones_usuarios1_idx` (`usuario_id`),
  ADD KEY `fk_publicacion_categoria1_idx` (`categoria_publicacion_id`);

--
-- Indexes for table `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `correo_UNIQUE` (`correo`),
  ADD KEY `fk_usuario_rol1_idx` (`rol_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categoria_publicacion`
--
ALTER TABLE `categoria_publicacion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `comentario`
--
ALTER TABLE `comentario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `publicacion`
--
ALTER TABLE `publicacion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `rol`
--
ALTER TABLE `rol`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `comentario`
--
ALTER TABLE `comentario`
  ADD CONSTRAINT `fk_comentario_publicacion1` FOREIGN KEY (`publicacion_id`) REFERENCES `publicacion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_comentario_usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `publicacion`
--
ALTER TABLE `publicacion`
  ADD CONSTRAINT `fk_publicacion_categoria1` FOREIGN KEY (`categoria_publicacion_id`) REFERENCES `categoria_publicacion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_publicaciones_usuarios1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `fk_usuario_rol1` FOREIGN KEY (`rol_id`) REFERENCES `rol` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
