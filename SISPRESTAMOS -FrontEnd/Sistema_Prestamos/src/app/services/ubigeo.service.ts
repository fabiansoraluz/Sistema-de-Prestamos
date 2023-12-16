import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import { DistritoDTO } from '../modelos/DistritoDTO';

@Injectable({
  providedIn: 'root'
})
export class UbigeoService {

  host:string="http://localhost:8091/api/ubigeo/"

  constructor(private http:HttpClient) { }

  public departamentos():Observable<any>{
    return this.http.get<string[]>(this.host+"departamento")
  }
  public provincias(departamento:string):Observable<any>{
    return this.http.get<string[]>(this.host+"provincia/"+departamento)
  }
  public distrito(provincia:string):Observable<any>{
    return this.http.get<DistritoDTO[]>(this.host+"distrito/"+provincia)
  }
}
