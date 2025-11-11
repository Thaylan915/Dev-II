import React, { useEffect, useState } from 'react';
import { api, getErrorMessage } from '../api';

export default function ClienteForm({ onSaved }){
  const [form, setForm] = useState({ tipo:'SOCIO', nome:'', ativo:true, socioId:'' });
  const [socios, setSocios] = useState([]);
  const [error, setError] = useState('');

  const loadSocios = async () => {
    try {
      const res = await api.get('/clientes');
      setSocios(res.data.filter(c => c.tipo==='SOCIO'));
    } catch {}
  };
  useEffect(()=>{ loadSocios(); }, []);

  const onChange = (k,v)=> setForm(s=>({...s,[k]:v}));

  const submit = async () => {
    setError('');
    try {
      const payload = {
        tipo: form.tipo,
        nome: form.nome,
        ativo: !!form.ativo,
      };
      if (form.tipo === 'DEPENDENTE') {
        if (!form.socioId) {
          setError('Dependente deve referenciar um Sócio.');
          return;
        }
        payload.socio = { id: Number(form.socioId) };
      }
      await api.post('/clientes', payload);
      setForm({ tipo:'SOCIO', nome:'', ativo:true, socioId:'' });
      onSaved?.();
    } catch(e){ setError(getErrorMessage(e)); }
  };

  //  bloquear ativar dependente sem sócio
  const bloquearAtivo = form.tipo==='DEPENDENTE' && !form.socioId;

  return (
    <div style={{display:'flex', flexDirection:'column', gap:8, padding:'8px 0'}}>
      <h3>Novo Cliente</h3>
      {error && <div style={{color:'crimson'}}>⚠ {error}</div>}

      <div>
        <label>Tipo:&nbsp;</label>
        <select value={form.tipo} onChange={e=>onChange('tipo', e.target.value)}>
          <option value="SOCIO">SOCIO</option>
          <option value="DEPENDENTE">DEPENDENTE</option>
        </select>
      </div>

      <div>
        <label>Nome:&nbsp;</label>
        <input value={form.nome} onChange={e=>onChange('nome', e.target.value)} placeholder="Nome" />
      </div>

      {form.tipo==='DEPENDENTE' && (
        <div>
          <label>Sócio:&nbsp;</label>
          <select value={form.socioId} onChange={e=>onChange('socioId', e.target.value)}>
            <option value="">-- selecione --</option>
            {socios.map(s=>(
              <option key={s.id} value={s.id}>{s.id} - {s.nome}</option>
            ))}
          </select>
        </div>
      )}

      <div>
        <label>Ativo:&nbsp;</label>
        <input type="checkbox" checked={!!form.ativo}
               onChange={e=>onChange('ativo', e.target.checked)}
               disabled={bloquearAtivo} />
        {bloquearAtivo && <small style={{marginLeft:8}}>Defina o Sócio para ativar o dependente.</small>}
      </div>

      <button onClick={submit}>Salvar</button>
    </div>
  );
}
