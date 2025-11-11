import axios from 'axios';
export const api = axios.create({ baseURL: '/api' });

export function getErrorMessage(err) {
  if (err?.response?.data?.error) return err.response.data.error;
  if (err?.response?.data && typeof err.response.data === 'object') {
    // 400 de validação vira mapa de {campo: msg}
    const obj = err.response.data;
    const parts = Object.entries(obj).map(([k,v]) => `${k}: ${v}`);
    if (parts.length) return parts.join('\n');
  }
  if (err?.response?.data?.details) return err.response.data.details.join('; ');
  return err?.response?.data?.message || err.message || 'Erro desconhecido';
}
