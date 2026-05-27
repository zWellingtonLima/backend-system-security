<?php
require_once("../utils/db.php");

$conn = get_connection();

$clientes = query_fetch(
  $conn,
  "SELECT * FROM cliente"
);

mysqli_close($conn);
?>

<!DOCTYPE html>
<html lang="pt">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Gestao Hotel</title>
  <link rel="stylesheet" href="../css/styles.css">
</head>

<body>
  <main class="container">
    <h2>Listagem de clientes: </h2>

    <?php foreach ($clientes as $cliente): ?>
      <div class="item-list">
        Nome: <?= $cliente["nome"] ?><br>
        Data Nascimento: <?= $cliente["data_nascimento"] ?><br>
      </div>
    <?php endforeach ?>

    <br>
    <p>Número de clientes encontrados <?= count($clientes) ?></p>

    <a href="../index.html">Voltar ao menu de Gestao</a>
  </main>
</body>

</html>