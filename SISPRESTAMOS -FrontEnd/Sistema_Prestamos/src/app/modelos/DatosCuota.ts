export interface DatosCuota {
    id_prestamo: number;
    id_cuota: number;
    estado: number;
    nombre: string;
    numero: string;
    monto: number;
    monto_pagado: number;
    fecha_vencimiento: string;
    mora?: number;
    montoTotal?: number;
  }