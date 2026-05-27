<!-- Buscar clientes com o tipo -->
<!-- SELECT c.id, c.nome, c.data_nascimento, tc.tipo
FROM cliente c
JOIN tipo_cliente tc ON c.fk_tipo_cliente = tc.id -->

<!-- Buscar quarto com o tipo -->
<!-- SELECT q.id, q.preco_noite, q.num_quartos, tq.tamanho
FROM quarto q
JOIN tipo_quarto tq ON q.fk_tipo_quarto = tq.id -->

<!-- Buscar reserva com tudo -->
<!-- SELECT
r.id,
r.data_reserva,
r.data_checkin,
r.data_checkout,
c.nome AS cliente,
tq.tamanho AS tipo_quarto,
q.preco_noite,
er.estado
FROM reserva r
JOIN cliente c ON r.fk_id_cliente = c.id
JOIN quarto q ON r.fk_id_quarto = q.id
JOIN tipo_quarto tq ON q.fk_tipo_quarto = tq.id
JOIN estado_reserva er ON r.fk_estado_reserva = er.id -->

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