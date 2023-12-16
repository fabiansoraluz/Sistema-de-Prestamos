import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Prestamista } from 'src/app/modelos/Prestamista';
import { Ubigeo } from 'src/app/modelos/Ubigeo';
import { CrudprestamistasService } from 'src/app/services/crudprestamistas.service';
import { ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';


@Component({
  selector: 'app-mantenimiento-prestamista',
  templateUrl: './mantenimiento-prestamista.component.html',
  styleUrls: ['./mantenimiento-prestamista.component.css']
})

export class MantenimientoPrestamistaComponent implements OnInit {
  public objPrestamista: Prestamista = new Prestamista();
  public listUbigeo: Ubigeo[] = []
  public isUpdating: boolean = false;
  public formulario:FormGroup

  constructor(
    private prestamistaService: CrudprestamistasService,
    private router: Router,
    private route: ActivatedRoute,
    private FB:FormBuilder) {
    prestamistaService.getUbigeo().subscribe(x => this.listUbigeo = x)
  }

  ngOnInit() {
    this.listarUbigeo();

    //Contruimos el formulario
    this.formulario = this.FB.group({
      username:['',
        [
          Validators.required,
          Validators.pattern("(?!\\s+)(?=.*[a-z])[a-zA-ZáéíóúÁÉÍÓÚñ.\\d]{6,12}")
        ]
      ],
      correo:['',
        [
          Validators.required,
          Validators.email,
          Validators.pattern("(?![0-9])[a-zA-ZáéíóúüÁÉÍÓÚÜñ\\d]+[@][a-zA-ZáéíóúüÁÉÍÓÚÜñ]+[.][a-zA-Z]{2,3}")
        ]
      ],
      nombre:['',
        [
          Validators.required,
          Validators.pattern("(?!\\s+)[a-zA-ZáéíóúüÁÉÍÓÚÜñ\\s]{4,30}")
        ]
      ],
      paterno:['',
        [
          Validators.required,
          Validators.pattern("(?!\\s+)[a-zA-ZáéíóúüÁÉÍÓÚÜñ]{3,30}")
        ]
      ],
      materno:['',
        [
          Validators.required,
          Validators.pattern("(?!\\s+)[a-zA-ZáéíóúüÁÉÍÓÚÜñ]{3,30}")
        ]
      ],
      celular:['',
        [
          Validators.required,
          Validators.pattern("(?![0])[\\d]{9}")
        ]
      ],
      dni:['',
        [
          Validators.required,
          Validators.pattern("[\\d]{8}"),
          Validators.min(5000)
        ]
      ],
      grupo:['',
        [Validators.required]
      ],
      ubigeo:['',
        [
          Validators.required,
          Validators.pattern("[\\d]{6}")
        ]
      ]
    })

    //Seteamos el grupo del prestamista para registrar
    const grupo = this.route.snapshot.params['registrar'];
    if(grupo){
      this.formulario.patchValue({
        grupo: grupo
      })
    }

    //Seteamos el prestamista para actualizar
    const idPrestamista = this.route.snapshot.params['actualizar'];
    if (idPrestamista) {
      // Si hay un ID en la ruta, estamos actualizando, no registrando
      this.isUpdating = true;
      this.prestamistaService.buscarPrestamistaPorId(idPrestamista).subscribe(
        (prestamista: Prestamista) => {
          this.objPrestamista = prestamista;
          this.formulario.patchValue({
            username:prestamista.usuario.username,
            correo:prestamista.usuario.correo,
            nombre:prestamista.usuario.persona.nombre,
            paterno:prestamista.usuario.persona.paterno,
            materno:prestamista.usuario.persona.materno,
            celular:prestamista.usuario.persona.celular,
            dni:prestamista.usuario.persona.dni,
            grupo:prestamista.grupo.id,
            ubigeo:prestamista.usuario.persona.ubigeo.id
          })
        },
        (error) => {
          Swal.fire("Error", "No se pudo cargar la información del prestamista.", "error");
        }
      );
    }

  }

  btnRegistrar() {

    this.buildUser();

    if (this.isUpdating) {
      this.actualizar();
      
    } else {
      //Agregamos clave por defecto
      this.objPrestamista.usuario.password = "test";
      this.registrar()
    }
  }
  registrar(){
    this.prestamistaService.registrarPrestamista(this.objPrestamista).subscribe(
      x => {
        // Nos redireccionamos al listado de prestamista
        this.router.navigate(['/admin/listado-prestamista']);

        // Mostramos un mensaje de registro
        Swal.fire("Registro exitoso", "El prestamista " + x.usuario.persona.nombre + " fue registrado.", "success");
      },
      err => {
        if (err.error && err.error.mensaje) {
          Swal.fire("Error en el registro", err.error.mensaje, "error");
        } else {
          Swal.fire("Error en el registro", "Ocurrió un error durante el registro.", "error");
        }
      }
    );
  }
  actualizar(){
    this.prestamistaService.actualizarPrestamista(this.objPrestamista).subscribe(
  
      (response) => {
        // Mostrar un mensaje de actualización exitosa
        Swal.fire("Actualización exitosa", "El prestamista " + response.usuario.persona.nombre + " fue actualizado.", "success");
        // Redirigir al listado de prestamistas
        this.router.navigate(['/admin/listado-prestamista']);
      },
      (err) => {
        // Manejar el error según tus necesidades, por ejemplo, mostrar un mensaje al usuario.
        Swal.fire("Error en la actualización", err.error.mensaje, "error");
      }
    );
  }
  buildUser(){
    this.objPrestamista.usuario.username          = this.formulario.get("username").value
    this.objPrestamista.usuario.correo            = this.formulario.get("correo").value
    this.objPrestamista.usuario.persona.nombre    = this.formulario.get("nombre").value
    this.objPrestamista.usuario.persona.paterno   = this.formulario.get("paterno").value
    this.objPrestamista.usuario.persona.materno   = this.formulario.get("materno").value
    this.objPrestamista.usuario.persona.celular   = this.formulario.get("celular").value
    this.objPrestamista.usuario.persona.dni       = this.formulario.get("dni").value
    this.objPrestamista.grupo.id                  = this.formulario.get("grupo").value
    this.objPrestamista.usuario.persona.ubigeo.id = this.formulario.get("ubigeo").value
  }

  listarUbigeo() {
    this.prestamistaService.getUbigeo().subscribe(x => this.listUbigeo = x)
  }

}
