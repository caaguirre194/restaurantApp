<?php
//1. Invoca los datos de conexión
include_once("conexion.php");
//2. Crear conexión a la Base de Datos
$con=mysqli_connect($host, $usuario, $clave, $bd) or die ('Falló la conexión');
mysqli_set_charset($con,"utf8");
//3. Tomar los campos provenientes del Formulario
$variable1 = trim($_GET['id']);
$variable2 = trim($_GET['perfil']);
$variable3 = trim($_GET['clave']);
$variable4 = trim($_GET['nombre']);
$variable5 = trim($_GET['apellido']);
$variable6 = trim($_GET['email']);

//4. Insertar campos en la Base de Datos
$inserta = "INSERT INTO $bd.usuario (id, perfil, clave, nombre, apellido, email)
VALUES ('$variable1','$variable2','$variable3','$variable4','$variable5','$variable6');";
$resultado = mysqli_query($con, $inserta);
echo json_encode ($resultado);
//5. Cerrar la conexión a la Base de Datos
mysqli_close($con);
// http://localhost/delicias/insertarUsuario.php?id=2020&perfil=Administrador&clave=123&nombre=Felipe&apellido=Ramirez&email=felipe2@gmail.com
?>


