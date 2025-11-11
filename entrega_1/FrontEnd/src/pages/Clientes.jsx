import React, { useEffect, useState } from 'react';
import {
  Paper, Stack, Button, Table, TableHead, TableRow, TableCell, TableBody,
  IconButton, Dialog, DialogTitle, DialogContent, DialogActions,
  TextField, Checkbox, FormControlLabel, MenuItem, Select, InputLabel, FormControl
} from '@mui/material';
import { Edit, Delete, Add } from '@mui/icons-material';
import { api, getErrorMessage } from '../api';
import ErrorAlert from '../components/ErrorAlert.jsx';

const sexos = ['MASCULINO','FEMININO','OUTRO'];
const tipos = ['SOCIO','DEPENDENTE'];

export default function Clientes(){
  const [rows, setRows] = useState([]);
  const [error, setError] = useState('');
  const [open, setOpen] = useState(false);
  const [form, setForm] = useState({
    id:null, nome:'', sexo:'', cpf:'', dataNascimento:'', ativo:true, tipo:'SOCIO'
  });

  const load = async ()=>{
    setError('');
    try{ const r = await api.get('/clientes'); setRows(Array.isArray(r.data)? r.data : []); }
    catch(e){ setError(getErrorMessage(e)); }
  };
  useEffect(()=>{ load(); }, []);

  const onSubmit = async ()=>{
    setError('');
    try{
      const payload = {
        id: form.id ?? undefined,
        nome: form.nome,
        sexo: form.sexo || undefined,
        cpf: form.cpf || undefined,
        dataNascimento: form.dataNascimento || undefined,
        ativo: !!form.ativo,
        tipo: form.tipo
      };
      if (!payload.nome?.trim()) { setError('Nome é obrigatório'); return; }
      if (form.id) await api.put(`/clientes/${form.id}`, payload);
      else await api.post('/clientes', payload);
      setOpen(false);
      setForm({ id:null, nome:'', sexo:'', cpf:'', dataNascimento:'', ativo:true, tipo:'SOCIO' });
      load();
    }catch(e){ setError(getErrorMessage(e)); }
  };

  const onDelete = async (id)=>{
    setError('');
    try{ await api.delete(`/clientes/${id}`); load(); }
    catch(e){ setError(getErrorMessage(e)); }
  };

  return (
    <Paper sx={{ p:2 }}>
      <ErrorAlert error={error} />

      <Stack direction="row" spacing={1} sx={{ mb:2 }}>
        <Button variant="contained" startIcon={<Add />} onClick={()=>{ setForm({ id:null, nome:'', sexo:'', cpf:'', dataNascimento:'', ativo:true, tipo:'SOCIO' }); setOpen(true); }}>
          Novo Cliente
        </Button>
        <Button onClick={load}>Atualizar</Button>
      </Stack>

      <Table size="small">
        <TableHead>
          <TableRow>
            <TableCell>ID</TableCell>
            <TableCell>Tipo</TableCell>
            <TableCell>Nome</TableCell>
            <TableCell>Sexo</TableCell>
            <TableCell>CPF</TableCell>
            <TableCell>Data Nasc.</TableCell>
            <TableCell>Ativo</TableCell>
            <TableCell align="right">Ações</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map(r=>(
            <TableRow key={r.id}>
              <TableCell>{r.id}</TableCell>
              <TableCell>{r.tipo}</TableCell>
              <TableCell>{r.nome}</TableCell>
              <TableCell>{r.sexo ?? '-'}</TableCell>
              <TableCell>{r.cpf ?? '-'}</TableCell>
              <TableCell>{r.dataNascimento ?? '-'}</TableCell>
              <TableCell>{r.ativo ? 'Sim' : 'Não'}</TableCell>
              <TableCell align="right">
                <IconButton onClick={()=>{ setForm({
                  id:r.id, nome:r.nome ?? '', sexo:r.sexo ?? '',
                  cpf:r.cpf ?? '', dataNascimento:r.dataNascimento ?? '',
                  ativo: !!r.ativo, tipo: r.tipo ?? 'SOCIO'
                }); setOpen(true); }}>
                  <Edit />
                </IconButton>
                <IconButton onClick={()=> onDelete(r.id)}>
                  <Delete />
                </IconButton>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>

      <Dialog open={open} onClose={()=>setOpen(false)}>
        <DialogTitle>{form.id ? 'Editar Cliente' : 'Novo Cliente'}</DialogTitle>
        <DialogContent sx={{ pt:2, display:'grid', gap:2, minWidth:380 }}>
          <TextField label="Nome" value={form.nome} onChange={e=>setForm({...form, nome:e.target.value})} />
          <FormControl>
            <InputLabel id="tipo-label">Tipo</InputLabel>
            <Select labelId="tipo-label" label="Tipo" value={form.tipo} onChange={e=>setForm({...form, tipo:e.target.value})}>
              {tipos.map(t => <MenuItem key={t} value={t}>{t}</MenuItem>)}
            </Select>
          </FormControl>
          <FormControl>
            <InputLabel id="sexo-label">Sexo</InputLabel>
            <Select labelId="sexo-label" label="Sexo" value={form.sexo} onChange={e=>setForm({...form, sexo:e.target.value})}>
              <MenuItem value="">(não informar)</MenuItem>
              {sexos.map(s => <MenuItem key={s} value={s}>{s}</MenuItem>)}
            </Select>
          </FormControl>
          <TextField label="CPF" value={form.cpf} onChange={e=>setForm({...form, cpf:e.target.value})} />
          <TextField type="date" label="Data de Nascimento" InputLabelProps={{shrink:true}}
            value={form.dataNascimento} onChange={e=>setForm({...form, dataNascimento:e.target.value})} />
          <FormControlLabel control={<Checkbox checked={form.ativo} onChange={e=>setForm({...form, ativo:e.target.checked})} />} label="Ativo" />
        </DialogContent>
        <DialogActions>
          <Button onClick={()=>setOpen(false)}>Cancelar</Button>
          <Button onClick={onSubmit} variant="contained">Salvar</Button>
        </DialogActions>
      </Dialog>
    </Paper>
  );
}
