import React, { useEffect, useState } from 'react';
import {
  Paper, Stack, TextField, Button, Table, TableHead, TableRow, TableCell,
  TableBody, IconButton, Dialog, DialogTitle, DialogContent, DialogActions
} from '@mui/material';
import { Edit, Delete, Add } from '@mui/icons-material';
import { api, getErrorMessage } from '../api';
import ErrorAlert from './ErrorAlert.jsx';


function getNewButtonLabel(title) {
  const map = {
    'Atores':   { artigo: 'Novo', singular: 'Ator' },
    'Diretores':{ artigo: 'Novo', singular: 'Diretor' },
    'Classes':  { artigo: 'Nova', singular: 'Classe' }, 
    'Clientes': { artigo: 'Novo', singular: 'Cliente' },
    'Títulos':  { artigo: 'Novo', singular: 'Título' },
    'Titulos':  { artigo: 'Novo', singular: 'Título' },
    'Itens':    { artigo: 'Novo', singular: 'Item' }
  };
  const cfg = map[title] || { artigo: 'Novo', singular: (title || '').replace(/s$/,'') || 'Registro' };
  return `${cfg.artigo} ${cfg.singular}`;
}

export default function SimpleListPage({ resource, title }){
  const [rows, setRows] = useState([]);
  const [error, setError] = useState('');
  const [open, setOpen] = useState(false);
  const [form, setForm] = useState({ id:null, nome:'' });

  const load = async ()=>{
    setError('');
    try{
      const res = await api.get(`/${resource}`);
      const data = res?.data;
      if (Array.isArray(data)) setRows(data);
      else if (Array.isArray(data?.content)) setRows(data.content);
      else { setRows([]); setError('Resposta inesperada do servidor.'); }
    }catch(e){ setError(getErrorMessage(e)); }
  };
  useEffect(()=>{ load(); }, []);

  const onSubmit = async ()=>{
    setError('');
    try{
      if (!form.nome?.trim()) { setError('Nome é obrigatório'); return; }
      if (form.id) await api.put(`/${resource}/${form.id}`, { id: form.id, nome: form.nome });
      else await api.post(`/${resource}`, { nome: form.nome });
      setOpen(false);
      setForm({ id:null, nome:'' });
      load();
    }catch(e){ setError(getErrorMessage(e)); }
  };

  const onDelete = async (id)=>{
    setError('');
    try{ await api.delete(`/${resource}/${id}`); load(); }
    catch(e){ setError(getErrorMessage(e)); }
  };

  return (
    <Paper sx={{ p: 2 }}>
      <ErrorAlert error={error} />

      <Stack direction="row" spacing={1} sx={{ mb:2 }}>
        <Button
          variant="contained"
          startIcon={<Add />}
          onClick={()=>{ setForm({id:null, nome:''}); setOpen(true); }}
        >
          {getNewButtonLabel(title)}
        </Button>
        <Button onClick={load}>Atualizar</Button>
      </Stack>

      <Table size="small">
        <TableHead>
          <TableRow>
            <TableCell>Nome</TableCell>
            <TableCell align="right">Ações</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map(r=>(
            <TableRow key={r.id}>
              <TableCell>{r.nome}</TableCell>
              <TableCell align="right">
                <IconButton onClick={()=>{ setForm({ id:r.id, nome:r.nome }); setOpen(true); }}>
                  <Edit />
                </IconButton>
                <IconButton onClick={()=> onDelete(r.id)}>
                  <Delete />
                </IconButton>
              </TableCell>
            </TableRow>
          ))}
          {rows.length === 0 && (
            <TableRow>
              <TableCell colSpan={2} align="center" style={{ opacity:.7, fontStyle:'italic' }}>
                Nenhum registro.
              </TableCell>
            </TableRow>
          )}
        </TableBody>
      </Table>

      <Dialog open={open} onClose={()=>setOpen(false)}>
        <DialogTitle>{getNewButtonLabel(title)}</DialogTitle>
        <DialogContent sx={{ pt: 2 }}>
          <TextField
            label="Nome"
            value={form.nome}
            onChange={e=>setForm({...form, nome:e.target.value})}
            fullWidth autoFocus
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={()=>setOpen(false)}>Cancelar</Button>
          <Button onClick={onSubmit} variant="contained">Salvar</Button>
        </DialogActions>
      </Dialog>
    </Paper>
  );
}
