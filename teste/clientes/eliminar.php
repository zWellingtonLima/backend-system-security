<?php
require_once("../utils/db.php");

$conn = get_connection();
$clientes = query_fetch($conn, "SELECT * FROM cliente");

if ($_SERVER['REQUEST_METHOD'] === "POST") {
  $cliente = (int) $_POST['cliente'];

  if ($cliente === 0) {
    $erro = "Preencha todos os campos, por favor.";
  } else {
    $result = query_execute(
      $conn,
      "DELETE FROM cliente WHERE id = ?",
      'i',
      [$cliente]
    );

    if ($result) {
      header("Location: ./eliminar1.php");
      exit;
    } else {
      $erro = "Ocorreu um erro ao remover o cliente.";
    }
  }
}
mysqli_close($conn);
?>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Document</title>
  <link rel="stylesheet" href="../css/styles.css">
</head>

<body>
  <main class="container">

    <h1>Eliminar Cliente</h1>

    <?php if (!empty($erro)): ?>
      <p style="color:red"><?= $erro ?></p>
    <?php endif ?>

    <form action="" method="post">
      <select name="cliente" id="cliente">
        <option value="">Escolha o cliente</option>
        <?php foreach ($clientes as $cliente): ?>
          <option value="<?= $cliente["id"] ?>"><?= $cliente["nome"] ?></option>
        <?php endforeach ?>
      </select>
      <input type="submit" value="Inserir">
    </form>
  </main>
</body>

</html>