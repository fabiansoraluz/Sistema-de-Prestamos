import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CuentaBancaria } from '../modelos/CuentaBancaria';
import { Observable, switchMap, throwError } from 'rxjs';
import { TipoCuenta } from '../modelos/TipoCuenta';


@Injectable({
  providedIn: 'root'
})
export class CuentaService {

  private apiBase = 'http://localhost:8091/api/cuenta';

  constructor(private http: HttpClient) { }

  // Método para obtener todas las cuentas bancarias
  listarCuentasBancarias(): Observable<CuentaBancaria[]> {
    return this.http.get<CuentaBancaria[]>(`${this.apiBase}/listar`);
  }

  // Método para obtener todos los tipos
  listarTiposCuentas(): Observable<TipoCuenta[]> {
    return this.http.get<TipoCuenta[]>(`${this.apiBase}/tipo`);
  }

  // Método para obtener todas las cuentas bancarias por ID Usuario
  listarCuentasXIDUsuario(id:number): Observable<CuentaBancaria[]> {
    return this.http.get<CuentaBancaria[]>(`${this.apiBase}/usuario/${id}`);
  }

  // Método para obtener una cuenta bancaria por su ID
  buscarCuentaBancariaPorId(id: number): Observable<CuentaBancaria> {
    return this.http.get<CuentaBancaria>(`${this.apiBase}/buscar/${id}`);
  }

  // Método para registrar una cuenta bancaria
  registrarCuentaBancaria(cuentaBancaria: CuentaBancaria): Observable<CuentaBancaria> {
    return this.http.post<CuentaBancaria>(`${this.apiBase}/registrar`, cuentaBancaria);
  }

  // Método para actualizar una cuenta bancaria por su ID
  actualizarCuentaBancaria(cuentaBancaria: CuentaBancaria): Observable<CuentaBancaria> {
    return this.http.put<CuentaBancaria>(`${this.apiBase}/actualizar`, cuentaBancaria);
  }

  // Método para eliminar una cuenta bancaria por su ID
  eliminarCuentaBancaria(id: number): Observable<any> {
    return this.http.delete(`${this.apiBase}/eliminar/${id}`);
  }
}
