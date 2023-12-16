import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Ubigeo } from '../modelos/Ubigeo';
import { Grupo } from '../modelos/Grupo';
import { Usuario } from '../modelos/Usuario';
import { JefeDTO } from '../modelos/JefeDTO';


@Injectable({
  providedIn: 'root'
})
export class CrudjefesService {


  private apiBase = 'http://localhost:8091/';

  constructor(private http: HttpClient) { }

  listarJefe():Observable<any>{
    return this.http.get<any>(`${this.apiBase}api/jefes/listarTodos`);
  }

  registrarJefe(Obj: any): Observable<any> {
    return this.http.post<any>(`${this.apiBase}api/jefes/registrar`, Obj);
  }

  actualizarJefe(Obj: any):Observable<any>{
    return this.http.put<any>(`${this.apiBase}api/jefes/actualizar`, Obj);
  }
  getUbigeo() : Observable<Ubigeo[]>{
    return this.http.get<Ubigeo[]>(`${this.apiBase}api/ubigeo/listar`);
  }
  getListaGrupos(){
    return this.http.get<Grupo[]>(this.apiBase+"api/jefes/listar")
  }
  eliminar(id:number):Observable<any>{
    return this.http.delete(`${this.apiBase}api/jefes/eliminar/${id}`)
  }

  buscarJefePorId(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiBase}api/jefes/buscar/${id}`);
  }

  obtenerIdJefePorPersona(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiBase}api/jefes/idUsuario/${id}`);
  }

  obtenerEstadistica(idJefe:number):Observable<any>{
    return this.http.get<any>(`${this.apiBase}api/jefes/obtenerEstadistica/${idJefe}`);
  }

}
