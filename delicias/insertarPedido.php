<?php
//1. Invoca los datos de conexión
include_once("conexion.php");
//2. Crear conexión a la Base de Datos
$con=mysqli_connect($host, $usuario, $clave, $bd) or die ('Falló la conexión');
mysqli_set_charset($con,"utf8");
//3. Tomar los campos provenientes del Formulario

$variable3 = trim($_GET['usuario']);
$variable4 = trim($_GET['alimento']);
$variable5 = trim($_GET['bebida']);

//4. Insertar campos en la Base de Datos
$inserta = "INSERT INTO $bd.pedido (usuario, alimento, bebida)
VALUES ('$variable3','$variable4','$variable5');";
$resultado = mysqli_query($con, $inserta);
echo json_encode ($resultado);
//5. Cerrar la conexión a la Base de Datos
mysqli_close($con);
// http://localhost/delicias/insertarPedido.php?usuario=1&alimento=1&bebida=1
?>


