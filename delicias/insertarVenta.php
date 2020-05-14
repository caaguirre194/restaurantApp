<?php
//1. Invoca los datos de conexión
include_once("conexion.php");
//2. Crear conexión a la Base de Datos
$con=mysqli_connect($host, $usuario, $clave, $bd) or die ('Falló la conexión');
mysqli_set_charset($con,"utf8");
//3. Tomar los campos provenientes del Formulario
$variable2 = trim($_GET['fecha']);
$variable3 = trim($_GET['cantidad']);

//4. Insertar campos en la Base de Datos
$inserta = "INSERT INTO $bd.venta (fecha, cantidad)
VALUES ('$variable2','$variable3');";
$resultado = mysqli_query($con, $inserta);
echo json_encode ($resultado);
//5. Cerrar la conexión a la Base de Datos
mysqli_close($con);
// http://localhost/delicias/insertarVenta.php?fecha=2020-05-14&cantidad=5
?>


