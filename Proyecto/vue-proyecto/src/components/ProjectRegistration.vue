<template>
  <div>
    <h2>Alta de Proyecto</h2>
    <form @submit.prevent="insertProject" class="styleForm">
      <div class="fieldsContainer">

        <v-col cols="12" sm="4" md="3" class="fieldMargin">
          <v-text-field v-model="descripcion" id="descripcion" label="Descripción" type="text" />
        </v-col>

        <!-- <div class="fieldMargin">
          <label for="fechaInicio">Fecha de Inicio: </label>
          <input v-model="fechaInicio" id="fechaInicio" type="text">
        </div>

        <div class="fieldMargin">
          <label for="fechaFin">Fecha de Fin: </label>
          <input v-model="fechaFin" id="fechaFin" type="text">
        </div> -->
        <v-col cols="12" sm="4" md="3" class="fieldMargin">

          <v-menu ref="menufInicio" v-model="menufInicio" :close-on-content-click="false" :return-value.sync="fechaInicio"
            transition="scale-transition" offset-y min-width="auto">
            <template v-slot:activator="{ on, attrs }">
              <v-text-field v-model="fechaInicio" label="Fecha de inicio" readonly v-bind="attrs"
                v-on="on"></v-text-field>
            </template>
            <v-date-picker v-model="fechaInicio" no-title scrollable>
              <v-spacer></v-spacer>
              <v-btn text color="primary" @click="menufInicio = false">
                Cancel
              </v-btn>
              <v-btn text color="primary" @click="$refs.menufInicio.save(fechaInicio)">
                OK
              </v-btn>
            </v-date-picker>
          </v-menu>
        </v-col>

        <v-col cols="12" sm="4" md="3" class="fieldMargin">

          <v-menu ref="menu" v-model="menu" :close-on-content-click="false" :return-value.sync="fechaFin"
            transition="scale-transition" offset-y min-width="auto">
            <template v-slot:activator="{ on, attrs }">
              <v-text-field v-model="fechaFin" label="Fecha de finalización" readonly v-bind="attrs"
                v-on="on"></v-text-field>
            </template>
            <v-date-picker v-model="fechaFin" no-title scrollable>
              <v-spacer></v-spacer>
              <v-btn text color="primary" @click="menu = false">
                Cancel
              </v-btn>
              <v-btn text color="primary" @click="$refs.menu.save(fechaFin)">
                OK
              </v-btn>
            </v-date-picker>
          </v-menu>
        </v-col>


        <v-col cols="12" sm="4" md="3" class="fieldMargin">
          <v-text-field v-model="lugar" id="lugar" label="Lugar" type="text" />
        </v-col>

        <v-col cols="12" sm="4" md="3" class="fieldMargin">
          <v-text-field v-model="observaciones" id="observaciones" label="Observaciones" type="text" />
        </v-col>

      </div>

      <div class="buttonsContainer">
        <v-btn type="submit" color="primary" class="btn">Aceptar</v-btn>
        <v-btn type="button" @click="close" class="btn">Cancelar</v-btn>
      </div>
      <!-- <p v-if="errorMessage">{{ errorMessage }}</p> -->
    </form>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data() {
    return {
      descripcion: '',
      fechaInicio: '',
      fechaFin: '',
      lugar: '',
      observaciones: '',
      menufInicio: false,
      menu: false
    }
  },
  methods: {
    checkFields() {
      const fields = [this.descripcion, this.fechaInicio, this.fechaFin,
      this.lugar, this.observaciones]

      return fields.every(field => field.trim() != '')
    },
    getProjects() {
      const URL = `http://localhost:8080/projects`

      axios.get(URL)
        .then(response => {
          this.projects = response.data
        })
        .catch((error) => {
          console.error("Error recuperando los datos: ", error)
        })
    },
    insertProject() {
      if (this.checkFields()) {

        try {
          const projectInfo = {
            descripcion: this.descripcion,
            fechaInicio: this.fechaInicio,
            fechaFin: this.fechaFin,
            lugar: this.lugar,
            observaciones: this.observaciones,
          }
          axios.post('http://localhost:8080/projects/insert', projectInfo)
          this.$router.push('/projects')

        } catch (error) {
          console.error("Error al guardar: ", error)
        }

        this.getProjects();

      } else {
        alert("Es obligatorio introducir todos los datos para dar de alta un nuevo proyecto")
      }
    },
    close() {
      this.$router.push('/projects')
    }
  },
  mounted() {
    // this.getEmployees()
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
  border: 1px solid blue;
  width: 250px;
}

.btn {
  width: 20%;
  margin: 7px;
}
</style>