import { Component, OnInit } from '@angular/core';
import { Estadistica } from 'src/app/modelos/Estadistica';
import { Usuario } from 'src/app/modelos/Usuario';
import { CrudjefesService } from 'src/app/services/crudjefes.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-ver-finanzas',
  templateUrl: './ver-finanzas.component.html',
  styleUrls: ['./ver-finanzas.component.css']
})
export class VerFinanzasComponent implements OnInit{

  public jefes:[Usuario]
  public idJefe=0;

  public lista:[Estadistica]
  public final:Estadistica = new Estadistica();

  constructor(private SJefe:CrudjefesService){}

  ngOnInit(): void {
    this.SJefe.listarJefe().subscribe(
      (response) => this.jefes=response
    )
  }

  public cambiarGrupo(){
    // Listamos el cuadro estadistico
    this.SJefe.obtenerEstadistica(this.idJefe).subscribe(
      (response) => {
        this.lista=response
        this.calcularTotal();
      },
      (err) =>{
        Swal.fire("Información del Sistema",err.error.mensaje,"info")
        this.lista=null
        this.final=new Estadistica();
      }
    )
  }   
  public calcularTotal(){
    var bean = new Estadistica();
    this.lista.forEach( function(row){
      bean.prestado    += row.prestado;
      bean.pagado      += row.pagado;
      bean.pendiente   += row.pendiente;
      bean.interes     += row.interes;
      bean.mora        += row.mora;
    })

    // Calculamos rentabilidad
    var rent = (bean.mora+bean.interes)/bean.prestado;
    bean.rentabilidad = rent
    this.final=bean
  }
}