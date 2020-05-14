<?php
include_once("conexion.php");
//1. Crear conexión a la Base de Datos
$con=mysqli_connect($host,$usuario,$clave,$bd) or die('Fallo la conexion');
mysqli_set_charset($con,"utf8");
//2. Tomar los campos provenientes de la tabla

$consulta = "SELECT p.id, u.nombre AS nombreUsuario, a.nombre AS nombreAlimento, b.nombre AS nombreBebida, a.precio AS precioAlimento, b.precio AS precioBebida, a.precio+b.precio AS total
FROM $bd.pedido AS p, $bd.usuario AS u, $bd.alimento AS a, $bd.bebida AS b
WHERE p.usuario = u.id AND p.alimento = a.id AND p.bebida = b.id";

$resultado = mysqli_query($con, $consulta);

$myArray = array();

    while($row = $resultado->fetch_array(MYSQLI_ASSOC)) {
            $myArray[] = $row;
    }
    echo json_encode($myArray);

mysqli_close($con);
// localhost/delicias/consultarPedido.php
?>