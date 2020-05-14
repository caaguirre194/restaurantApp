<?php
include_once("conexion.php");
//1. Crear conexión a la Base de Datos
$con=mysqli_connect($host,$usuario,$clave,$bd) or die('Fallo la conexion');
mysqli_set_charset($con,"utf8");
//2. Tomar los campos provenientes de la tabla
$consulta="SELECT * FROM $bd.usuario";
$resultado = mysqli_query($con, $consulta);


$myArray = array();

    while($row = $resultado->fetch_array(MYSQLI_ASSOC)) {
            $myArray[] = $row;
    }
    echo json_encode($myArray);

mysqli_close($con);
//localhost/delicias/consultarUsuario.php
?>