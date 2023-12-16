import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Prestamista } from '../modelos/Prestamista';
import { Ubigeo } from '../modelos/Ubigeo';


@Injectable({
  providedIn: 'root'
})
export class CrudprestamistasService {

  private apiBase = 'http://localhost:8091/';

  constructor(private http: HttpClient) { }


   // Método para registrar un prestamista
  registrarPrestamista(prestamista: Prestamista): Observable<Prestamista> {
    return this.http.post<Prestamista>(`${this.apiBase}api/prestamista/registrar`, prestamista);
  }

  // Método para actualizar un prestamista
  actualizarPrestamista(prestamista: Prestamista): Observable<Prestamista> {
    return this.http.put<Prestamista>(`${this.apiBase}api/prestamista/actualizar`, prestamista);
  }


  obtenerDetallesPrestamista(idPrestamista: number): Observable<Prestamista> {
    return this.http.get<Prestamista>(`${this.apiBase}api/prestamista/detalles/${idPrestamista}`);
  }

  getUbigeo(): Observable<Ubigeo[]> {
    return this.http.get<Ubigeo[]>(`${this.apiBase}api/ubigeo/listar`);
  }

  obtenerPrestamistasPorGrupo(idGrupo: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiBase}api/prestamista/listarPrestamistas/${idGrupo}`);
  }

  eliminarPrestamita(id: number): Observable<any> {
    return this.http.delete(this.apiBase + "api/prestamista/eliminar/" + id);
  }
  // Método para buscar un prestamista por ID
  buscarPrestamistaPorId(id: number): Observable<Prestamista> {
    return this.http.get<Prestamista>(`${this.apiBase}api/prestamista/buscar/${id}`);
  }

}
