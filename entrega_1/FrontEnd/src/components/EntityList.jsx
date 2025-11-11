import React, { useEffect, useState } from 'react';
import { api, getErrorMessage } from '../api';

export default function EntityList({ resource, fieldMap = ['nome'], title = 'Lista' }){
  const [data, setData] = useState([]);
  const [form, setForm] = useState({});
  const [editingId, setEditingId] = useState(null);
  const [error, setError] = useState('');

  const load = async () => {
    setError('');
    try {
      const res = await api.get(`/${resource}`);
      setData(res.data);
    } catch (e) { setError(getErrorMessage(e)); }
  };

  useEffect(()=>{ load(); }, [resource]);

  const onChange = (k,v)=> setForm(s=>({...s,[k]:v}));

  const submit = async () => {
    setError('');
    try {
      if (editingId) await api.put(`/${resource}/${editingId}`, form);
      else await api.post(`/${resource}`, form);
      setForm({}); setEditingId(null); load();
    } catch(e){ setError(getErrorMessage(e)); }
  };

  const edit = (row) => { setForm(row); setEditingId(row.id); setError(''); };
  const remove = async (id) => {
    setError('');
    try { await api.delete(`/${resource}/${id}`); load(); }
    catch(e){ setError(getErrorMessage(e)); }
  };

  return (
    <div>
      <h2>{title}</h2>
      {error && <div style={{color:'crimson', marginBottom:8}}>⚠ {error}</div>}
      <div style={{display:'flex', gap:8, margin:'8px 0'}}>
        {fieldMap.map(k => (
          <input key={k} placeholder={k} value={form[k]||''}
                 onChange={e=>onChange(k,e.target.value)} />
        ))}
        <button onClick={submit}>{editingId ? 'Salvar' : 'Criar'}</button>
        {editingId && <button onClick={()=>{setForm({}); setEditingId(null);}}>Cancelar</button>}
      </div>
      <table border="1" cellPadding="6">
        <thead><tr><th>ID</th>{fieldMap.map(k=><th key={k}>{k}</th>)}<th>Ações</th></tr></thead>
        <tbody>
          {data.map(row=>(
            <tr key={row.id}>
              <td>{row.id}</td>
              {fieldMap.map(k=><td key={k}>{String(row[k]??'')}</td>)}
              <td>
                <button onClick={()=>edit(row)}>Editar</button>
                <button onClick={()=>remove(row.id)}>Excluir</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
