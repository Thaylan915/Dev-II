import React, { useEffect, useState } from 'react';
import { Paper, Table, TableHead, TableRow, TableCell, TableBody, IconButton, Button, Stack, TextField, Select, MenuItem, InputLabel, FormControl } from '@mui/material';
import { Delete } from '@mui/icons-material';
import { api, getErrorMessage } from '../api';
import ErrorAlert from '../components/ErrorAlert.jsx';

export default function Itens() {
  const [rows, setRows] = useState([]);
  const [titulos, setTitulos] = useState([]);
  const [form, setForm] = useState({ numeroSerie: '', tituloId: '' });
  const [error, setError] = useState('');

  const load = async () => {
    setError('');
    try {
      const [itensRes, titulosRes] = await Promise.all([
        api.get('/itens'),
        api.get('/titulos')
      ]);
      setRows(Array.isArray(itensRes.data) ? itensRes.data : []);
      setTitulos(Array.isArray(titulosRes.data) ? titulosRes.data : []);
    } catch (e) { setError(getErrorMessage(e)); }
  };
  useEffect(() => { load(); }, []);

  const onCreate = async () => {
    setError('');
    try {
      await api.post('/itens', {
        numeroSerie: form.numeroSerie,
        titulo: { id: Number(form.tituloId) || null }
      });
      setForm({ numeroSerie: '', tituloId: '' });
      await load();
    } catch (e) { setError(getErrorMessage(e)); }
  };

  const onDelete = async (id) => {
    setError('');
    try { await api.delete(`/itens/${id}`); await load(); }
    catch (e) { setError(getErrorMessage(e)); }
  };

  return (
    <Paper sx={{ p:2 }}>
      <h2>Itens</h2>
      {error && <ErrorAlert message={error} />}

      <Stack direction="row" spacing={2} sx={{ mb:2 }}>
        <TextField
          label="Nome da Serie"
          value={form.numeroSerie}
          onChange={(e)=> setForm({ ...form, numeroSerie: e.target.value })}
        />
        <FormControl sx={{ minWidth: 240 }}>
          <InputLabel id="titulo-label">Título</InputLabel>
          <Select
            labelId="titulo-label"
            label="Título"
            value={form.tituloId}
            onChange={(e)=> setForm({ ...form, tituloId: e.target.value })}
          >
            {titulos.map(t => <MenuItem key={t.id} value={t.id}>{t.nome}</MenuItem>)}
          </Select>
        </FormControl>
        <Button variant="contained" onClick={onCreate}>Criar</Button>
      </Stack>

      <Table size="small">
        <TableHead>
          <TableRow>
            <TableCell>ID</TableCell>
            <TableCell>Nome da Série</TableCell>
            <TableCell>Título</TableCell>
            <TableCell align="right">Ações</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map(it => (
            <TableRow key={it.id}>
              <TableCell>{it.id}</TableCell>
              <TableCell>{it.numeroSerie}</TableCell>
              <TableCell>{it.titulo?.nome ?? '-'}</TableCell>
              <TableCell align="right">
                <IconButton onClick={()=> onDelete(it.id)}><Delete /></IconButton>
              </TableCell>
            </TableRow>
          ))}
          {rows.length === 0 && (
            <TableRow>
              <TableCell colSpan={4} align="center" style={{ opacity:.7, fontStyle:'italic' }}>
                Nenhum registro.
              </TableCell>
            </TableRow>
          )}
        </TableBody>
      </Table>
    </Paper>
  );
}
