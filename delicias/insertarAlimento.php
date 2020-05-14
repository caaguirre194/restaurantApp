<?php
//1. Invoca los datos de conexión
include_once("conexion.php");
//2. Crear conexión a la Base de Datos
$con=mysqli_connect($host, $usuario, $clave, $bd) or die ('Falló la conexión');
mysqli_set_charset($con,"utf8");
//3. Tomar los campos provenientes del Formulario
$variable2 = trim($_GET['tipo']);
$variable3 = trim($_GET['nombre']);
$variable4 = trim($_GET['precio']);

//4. Insertar campos en la Base de Datos
$inserta = "INSERT INTO $bd.alimento (tipo, nombre, precio)
VALUES ('$variable2','$variable3','$variable4');";
$resultado = mysqli_query($con, $inserta);
echo json_encode ($resultado);
//5. Cerrar la conexión a la Base de Datos
mysqli_close($con);
// http://localhost/delicias/insertarAlimento.php?tipo=frio&nombre=helado&precio=3000
// http://localhost/delicias/insertarAlimento.php?tipo=caliente&nombre=pizza&precio=7000
?>


