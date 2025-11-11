import React from 'react';
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import { CssBaseline, AppBar, Toolbar, Typography, Container, Box, Button } from '@mui/material';
import Atores from './pages/Atores.jsx';
import Diretores from './pages/Diretores.jsx';
import Classes from './pages/Classes.jsx';
import Clientes from './pages/Clientes.jsx';
import Titulos from './pages/Titulos.jsx';
import Itens from './pages/Itens.jsx';

export default function App(){
  return (
    <>
      <CssBaseline />
      <BrowserRouter>
        <AppBar position="static">
          <Toolbar>
            <Typography variant="h6" sx={{ flexGrow: 1 }}>Locadora — Sprint 1</Typography>
            <Button color="inherit" component={Link} to="/">Home</Button>
            <Button color="inherit" component={Link} to="/atores">Atores</Button>
            <Button color="inherit" component={Link} to="/diretores">Diretores</Button>
            <Button color="inherit" component={Link} to="/classes">Classes</Button>
            <Button color="inherit" component={Link} to="/clientes">Clientes</Button>
            <Button color="inherit" component={Link} to="/titulos">Títulos</Button>
            <Button color="inherit" component={Link} to="/itens">Itens</Button>

          </Toolbar>
        </AppBar>

        <Container maxWidth="lg">
          <Box sx={{ my: 3 }}>
            <Routes>
              <Route path="/" element={<Typography>Bem-vindo! Escolha um menu acima.</Typography>} />
              <Route path="/atores" element={<Atores />} />
              <Route path="/diretores" element={<Diretores />} />
              <Route path="/classes" element={<Classes />} />
              <Route path="/clientes" element={<Clientes />} />
              <Route path="/titulos" element={<Titulos />} />
              <Route path="/itens" element={<Itens />} />
            </Routes>
          </Box>
        </Container>
      </BrowserRouter>
    </>
  );
}
