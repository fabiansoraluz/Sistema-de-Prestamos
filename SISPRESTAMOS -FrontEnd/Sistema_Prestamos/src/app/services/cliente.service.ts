import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cliente } from '../modelos/Cliente';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  public host:string="http://localhost:8091/api/cliente"

  constructor(private http:HttpClient) { }

  public buscarXIDUsuario(idUsuario:number):Observable<any>{
    return this.http.get<Cliente>(this.host+"/usuario/"+idUsuario)
  }

}
