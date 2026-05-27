<?php
function get_connection()
{
  static $conn = null;

  if ($conn === null) {
    $conn = mysqli_connect("127.0.0.1", "root", "", "gestao_hotel");

    if (!$conn) die("Erro ao estabelecer conexão: " . mysqli_connect_error());
  }

  return $conn;
}

function query_fetch($conn, $sql, $types = '', $params = [])
{
  $stmt = mysqli_prepare($conn, $sql);

  if (!$stmt) die("Erro ao preparar a query: " . mysqli_error($conn));

  if ($types && $params) {
    mysqli_stmt_bind_param($stmt, $types, ...$params);
  }

  mysqli_stmt_execute($stmt);
  $result = mysqli_stmt_get_result($stmt);
  $rows = mysqli_fetch_all($result, MYSQLI_ASSOC);
  mysqli_stmt_close($stmt);

  return $rows;
}


function query_execute($conn, $sql, $types = '', $params = [])
{
  $stmt = mysqli_prepare($conn, $sql);

  if (!$stmt) die("Erro ao preparar a query: " . mysqli_error($conn));

  if ($types && $params) {
    mysqli_stmt_bind_param($stmt, $types, ...$params);
  }

  $success = mysqli_stmt_execute($stmt);
  mysqli_stmt_close($stmt);

  return $success;
}
