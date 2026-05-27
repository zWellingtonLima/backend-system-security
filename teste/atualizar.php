<?php
require_once("../utils/db.php");

$conn = get_connection();
$tipos = query_fetch($conn, "SELECT * FROM tipo_cliente");

if ($_SERVER['REQUEST_METHOD'] === "POST") {
  
  $nome = htmlspecialchars($_POST['nome']);
  $nasc = trim($_POST['nascimento']);
  $tipo = (int) $_POST['tipo_cliente'];

  if (empty($nome) || empty($nasc) || $tipo === 0) {
    $erro = "Preencha todos os campos, por favor.";
  } else {
    $result = query_execute(
      $conn,
      "UPDATE cliente SET nome = ?, data_nascimento = ?, fk_tipo_cliente = ? WHERE id = ?",
      'ssi',
      [$nome, $nasc, $tipo]
    );

    if ($result) {
      header("Location: ./inserir1.php");
      exit;
    } else {
      $erro = "Ocorreu um erro ao inserir o cliente.";
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

    <h1>Inserir Cliente</h1>

    <?php if (!empty($erro)): ?>
      <p style="color:red"><?= $erro ?></p>
    <?php endif ?>

    <form action="" method="post">
      <label for="nome">Nome:</label>
      <input type="text" name="nome" id="nome">

      <label for="nascimento">Data de Nascimento:</label>
      <input type="date" name="nascimento" id="nascimento">

      <select name="tipo_cliente" id="tipo_cliente">
        <option value="">Escolha o tipo de cliente</option>
        <?php foreach ($tipos as $tipo): ?>
          <option value="<?= $tipo["id"] ?>"><?= $tipo["tipo"] ?></option>
        <?php endforeach ?>
      </select>
      <input type="submit" value="Inserir">
    </form>
  </main>
</body>

</html>