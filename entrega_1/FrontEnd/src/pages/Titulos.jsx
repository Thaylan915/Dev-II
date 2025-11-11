import React, { useEffect, useState } from 'react';
import { Paper, Table, TableHead, TableRow, TableCell, TableBody, IconButton, Button, Stack } from '@mui/material';
import { Delete } from '@mui/icons-material';
import { api, getErrorMessage } from '../api';
import ErrorAlert from '../components/ErrorAlert.jsx';

export default function Titulos(){
  const [rows, setRows] = useState([]);
  const [error, setError] = useState('');

  const load = async ()=>{
    setError('');
    try{ const r = await api.get('/titulos'); setRows(Array.isArray(r.data)? r.data : []); }
    catch(e){ setError(getErrorMessage(e)); }
  };
  useEffect(()=>{ load(); }, []);

  const onDelete = async (id)=>{
    setError('');
    try{ await api.delete(`/titulos/${id}`); load(); }
    catch(e){ setError(getErrorMessage(e)); }
  };

  return (
    <Paper sx={{ p:2 }}>
      <ErrorAlert error={error} />
      <Stack direction="row" spacing={1} sx={{ mb:2 }}>
        <Button onClick={load}>Atualizar</Button>
      </Stack>
      <Table size="small">
        <TableHead>
          <TableRow>
            <TableCell>ID</TableCell>
            <TableCell>Nome</TableCell>
            <TableCell>Ano</TableCell>
            <TableCell>Classe </TableCell>
            <TableCell align="right">Ações</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map(t=>(
            <TableRow key={t.id}>
              <TableCell>{t.id}</TableCell>
              <TableCell>{t.nome}</TableCell>
              <TableCell>{t.ano ?? '-'}</TableCell>
              <TableCell>{t.classe?.nome ?? '-'}</TableCell>
              <TableCell align="right">
                <IconButton onClick={()=> onDelete(t.id)}><Delete /></IconButton>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </Paper>
  );
}
