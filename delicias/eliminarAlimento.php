<?php
//1. Invoca los datos de conexión
include_once("conexion.php");
//2. Crear conexión a la Base de Datos
$con=mysqli_connect($host, $usuario, $clave, $bd) or die ('Falló la conexión');
mysqli_set_charset($con,"utf8");
//3. Tomar los campos provenientes del Formulario
$variable1 = trim($_GET['id']);
//4. Elimina campos en la Base de Datos
$elimina = "DELETE FROM $bd.alimento WHERE id='$variable1';";
$resultado = mysqli_query($con, $elimina);
echo json_encode ($resultado);
//5. Cerrar la conexión a la Base de Datos
mysqli_close($con);
// http://localhost/delicias/eliminarAlimento.php?id=1
?>


