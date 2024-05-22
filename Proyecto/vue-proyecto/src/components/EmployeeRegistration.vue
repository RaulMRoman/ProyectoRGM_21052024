<template>
  <div>
    <h2>Alta de Empleado</h2>
    <form @submit.prevent="insertEmployee" class="styleForm">
      <row class="fieldsContainer">
        <v-col cols="12" sm="4" md="3" class="fieldMargin">
          <v-text-field v-model="nif" id="nif" label="NIF" type="text" />
        </v-col>

        <v-col cols="12" sm="4" md="3" class="fieldMargin">
          <v-text-field v-model="nombre" id="nombre" label="Nombre" type="text" />
        </v-col>

        <v-col cols="12" sm="4" md="3" class="fieldMargin">
          <v-text-field v-model="primerApellido" id="primerApellido" label="Primer Apellido" type="text" />
        </v-col>

        <v-col cols="12" sm="4" md="3" class="fieldMargin">
          <v-text-field v-model="segundoApellido" id="segundoApellido" label="Segundo Apellido"
            type="text"></v-text-field>
        </v-col>

        <!-- <div class="fieldMargin">
          <label for="fNacimiento">Fecha de Nacimiento: </label>
          <input v-model="fNacimiento" id="fNacimiento" type="text" class="borderInput">
        </div> -->

        <v-col cols="12" sm="4" md="3" class="fieldMargin">

          <v-menu ref="menu" v-model="menu" :close-on-content-click="false" :return-value.sync="fNacimiento"
            transition="scale-transition" offset-y min-width="auto">
            <template v-slot:activator="{ on, attrs }">
              <v-text-field v-model="fNacimiento" label="Fecha de nacimiento" readonly v-bind="attrs"
                v-on="on"></v-text-field>
            </template>
            <v-date-picker v-model="fNacimiento" no-title scrollable>
              <v-spacer></v-spacer>
              <v-btn text color="primary" @click="menu = false">
                Cancel
              </v-btn>
              <v-btn text color="primary" @click="$refs.menu.save(fNacimiento)">
                OK
              </v-btn>
            </v-date-picker>
          </v-menu>
        </v-col>

        <v-col cols="12" sm="4" md="3" class="fieldMargin">
          <v-text-field v-model="telefonoUno" id="telefonoUno" label="Teléfono Uno" type="text" />
        </v-col>

        <v-col cols="12" sm="4" md="3" class="fieldMargin">
          <v-text-field v-model="telefonoDos" id="telefonoDos" label="Teléfono Dos" type="text" />
        </v-col>

        <v-col cols="12" sm="4" md="3" class="fieldMargin">
          <v-text-field v-model="email" id="email" label="Email" type="text" />
        </v-col>

        <!-- <v-col cols="12" sm="4" md="3" class="fieldMargin">
          <v-text-field v-model="fechaAlta" id="fechaAlta" type="text"/>
        </v-col> -->

        <v-col cols="12" sm="4" md="3" class="fieldMargin">

          <v-menu ref="menuAlta" v-model="menuAlta" :close-on-content-click="false" :return-value.sync="fechaAlta"
            transition="scale-transition" offset-y min-width="auto">
            <template v-slot:activator="{ on, attrs }">
              <v-text-field v-model="fechaAlta" label="Fecha de Alta" readonly v-bind="attrs" v-on="on"></v-text-field>
            </template>
            <v-date-picker v-model="fechaAlta" no-title scrollable>
              <v-spacer></v-spacer>
              <v-btn text color="primary" @click="menuAlta = false">
                Cancel
              </v-btn>
              <v-btn text color="primary" @click="$refs.menuAlta.save(fechaAlta)">
                OK
              </v-btn>
            </v-date-picker>
          </v-menu>
        </v-col>

        <!-- <v-col cols="12" sm="4" md="3" class="fieldMargin">
          <v-text-field v-model="estadoCivil" id="estadoCivil" type="text" />
        </v-col>

        <v-col cols="12" sm="4" md="3" class="fieldMargin">
          <v-text-field v-model="servMilitar" id="servMilitar" type="text" />
        </v-col> -->
          
              <v-col cols="12" sm="6" md="4" class="fieldMargin">
                <v-select :items="items" v-model="estadoCivil" id="estadoCivil" label="Estado Civil" type="text" dense></v-select>
              </v-col>

              <v-col cols="12" sm="6" md="4" class="fieldMargin">
                <v-select :items="itemsServMilitar" v-model="servMilitar" id="servMilitar" type="text" label="Servicio Militar" dense></v-select>
              </v-col>
      </row>

      <div class="buttonsContainer">
        <v-btn type="submit" color="primary" class="btn">Aceptar</v-btn>
        <v-btn type="button" @click="close" class="btn">Cancelar</v-btn>
      </div>
      <!-- <p v-if="errorMessage">{{ errorMessage }}</p> -->
    </form>
  </div>
</template>

<script>
  export default {
    data: () => ({
      
    }),
  }
</script>

<script>
import axios from 'axios'
export default {
  data() {
    return {
      nif: '',
      nombre: '',
      primerApellido: '',
      segundoApellido: '',
      fNacimiento: '',
      telefonoUno: '',
      telefonoDos: '',
      email: '',
      fechaAlta: '',
      estadoCivil: '',
      servMilitar: '',
      errorMessage: '',
      menu: false,
      menuAlta: false,
      items: ['S', 'C'],
      itemsServMilitar: ['S', 'N'],
    }
  },
  methods: {
    //Método checkFields para comprobar que no están vacíos
    checkFields() {
      const fields = [this.nif, this.nombre, this.primerApellido, this.segundoApellido,
      this.fNacimiento, this.telefonoUno, this.telefonoDos,
      this.email, this.fechaAlta,
      this.estadoCivil, this.servMilitar]

      return fields.every(field => field.trim() != '')
    },
    //Solicitud de la lista de Empleados
    async getEmployees() {
      const URL = `http://localhost:8080/empleados`

      await axios.get(URL)
        .then(response => {
          this.employees = response.data
        })
        .catch((error) => {
          console.error("Error recuperando los datos: " + error.response.data)
        })
    },
    //Solicitud para inserción de nuevo empleado
    async insertEmployee() {
      if (this.checkFields()) {

        try {
          //Asignación de valores con los que el usuario introduzca en la web
          const employeeInfo = {
            nif: this.nif,
            nombre: this.nombre,
            primerApellido: this.primerApellido,
            segundoApellido: this.segundoApellido,
            fNacimiento: this.fNacimiento,
            telefonoUno: this.telefonoUno,
            telefonoDos: this.telefonoDos,
            email: this.email,
            fechaAlta: this.fechaAlta,
            estadoCivil: this.estadoCivil,
            servMilitar: this.servMilitar
          }
          //const URL = `http://localhost:8080/empleados/insert`
          await axios.post('http://localhost:8080/empleados/insert', employeeInfo)
          this.$router.push('/prueba')

        } catch (error) {
          // alert("Error al guardar: {}", error.response.data)
          alert("Error al guardar: " + error.response.data)
          // console.log(error.response.data)

        }

        this.getEmployees();

      } else {
        alert("Es obligatorio introducir todos los datos para dar de alta un nuevo empleado")
      }
    },
    close() {
      this.$router.push('/prueba')
    }
  },
  mounted() {
  }
}
</script>

<style scoped>

h2{
  padding: 2%;
}
.fieldsContainer {
  display: flex;
  flex-wrap: wrap;
  /* justify-content: space-between; */
  margin: 5px;
}

.buttonsContainer {
  display: flex;
  justify-content: center;
  margin-top: 2%;
}

.fieldMargin {
  display: flex;
  flex-direction: column;
  width: 29%;
  margin: 2%;
}

.borderInput {
  border-bottom: 1px solid;
  width: 250px;
}

.btn {
  width: 20%;
  margin: 7px;
}
</style>