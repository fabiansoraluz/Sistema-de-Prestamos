import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PrestamoService {
  private apiUrl = 'http://localhost:8091/api/prestamos';  

  constructor(private http: HttpClient) { }

  public obtenerInformacionPrestamos(id_usuario: number, id_prestamo: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/obtenerInformacion?idUsuario=${id_usuario}&idPrestamo=${id_prestamo}`);
  }

  public obtenerInformacionPrestamosJefePrestamista(id_prestamo: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/obtenerInformacionJefePrestamista?idPrestamo=${id_prestamo}`);
  }

  public listarPrestamos(id_usuario: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/listar?idUsuario=${id_usuario}`);
  }

  public listarPrestamosPorJefePrestamista(id_usuario: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/listarPorJefePrestamista?idUsuario=${id_usuario}`);
  }

  actualizarMontoPagado(idPrestamo: number, montoIngresado: number): Observable<any> {
    const url = `${this.apiUrl}/actualizarMontoPagado?idPrestamo=${idPrestamo}&montoIngresado=${montoIngresado}`;
    return this.http.put(url, null); 
  }

  actualizarMoraPagada(idPrestamo: number, moraIngresada: number): Observable<any> {
    const url = `${this.apiUrl}/actualizarMoraPagada?idPrestamo=${idPrestamo}&moraIngresada=${moraIngresada}`;
    return this.http.put(url, null); 
  }

}