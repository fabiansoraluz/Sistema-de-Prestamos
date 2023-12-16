import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Solicitud } from '../modelos/Solicitud';
import { CuentaBancaria } from '../modelos/CuentaBancaria';
import { SolicitudDTO } from '../modelos/SolicitudDTO';

@Injectable({
  providedIn: 'root'
})
export class SolicitudService {

  private host="http://localhost:8091/api/solicitud"

  private host2="http://localhost:8091/api/prestamista"

  constructor(private http:HttpClient) { }

  /* METODOS PARA SOLICITUD */
  public listar():Observable<any>{
    return this.http.get(this.host);
  }
  public registrar(bean:Solicitud):Observable<any>{
    return this.http.post(this.host+"/registrar",bean);
  }
  public actualizarEstado(bean:Map<string,number>):Observable<any>{
    return this.http.post(this.host+"/estado",bean)
  }
  /* METODO PARA OBTENER EL GRUPO */
  public buscarIdGrupoXIdUsuario(idUsuario:number):Observable<any>{
    return this.http.get(this.host+"/seleccionGrupo/"+idUsuario)
  }


  //METODO PARA CARGAR SOLICITUDES DEL GRUPO
  public listaSolicitudGrupo(idGrupo:number):Observable<any>{
    return this.http.get(this.host+"/listaporGrupo/"+idGrupo)
  }

  //METODO PARA CARGAR SOLICITUDES DEL GRUPO
  public obtenerGrupodeUsuario(idUsuario:number):Observable<any>{
    return this.http.get(this.host+"/obtenerIdGrupo/"+idUsuario)
  }

  //METODO PARA CARGAR SOLICITUDES DEL GRUPO
  public obtenerGrupodeUsuarioPrestamista(idUsuario:number):Observable<any>{
    return this.http.get(this.host+"/obtenerIdGrupoPrestamista/"+idUsuario)
  }


  public obtenerSolicitudesPorUsuario(idUsuario: number): Observable<any> {
    return this.http.get(`${this.host}/porUsuario/${idUsuario}`);
  }

  //METODOS PARA APROBAR Y RECHAZAR SOLICITUD

  aprobarSolicitud(prestamo: any): Observable<any> {
    return this.http.put(`${this.host2}/aprobarSolicitud`, prestamo);
  }

  rechazarSolicitud(id: string): Observable<any> {
    return this.http.put(`${this.host2}/rechazarSolicitud/${id}`, null);
  }

  //
  public obtenerSolicitudPorId(idSolicitud: string): Observable<any> {
    return this.http.get(`${this.host}/buscarSolicitud/${idSolicitud}`);
  }

  //
  public obtenerIdPrestamista(id: number): Observable<any> {
    return this.http.get(`${this.host2}/obtenerIdPrestamista/${id}`);
  }


  //METODO PARA FILTRAR SOLICITUD
  public filtrarSolicitud(bean:SolicitudDTO):Observable<any>{
    return this.http.post(`${this.host}/filtrar`,bean)
  }





}
