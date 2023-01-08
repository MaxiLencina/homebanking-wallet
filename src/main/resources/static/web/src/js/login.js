const { createApp } = Vue;

createApp({
  data() {
    return {
      sign: false,
      email: "",
      password: "",
      firstName: "",
      lastName: "",
      mensajeError: "",
    };
  },

  created() {},

  methods: {
    clickSign() {
      this.sign = !this.sign;
    },

    signIn(event) {
      event.preventDefault();

      axios
        .post("/api/login", `email=${this.email}&password=${this.password}`)
        .then(
          (response) => (window.location.href = "/web/src/pages/accounts.html")
        )
        .catch(() => {
          this.mensajeError = "fallo el ingreso";
        });
    },

    /*   crearPrimeraCuenta() {
      axios
        .post("http://localhost:8080/api/clients/current/accounts")
        .then((response) => window.location.reload())
        .catch((err) => console.log(err));
    }, */

    /*  crearPrimerCuenta() {
      return axios.post("/api/clients/current/accounts").then((res) => {
        if (res.status == 201) {
          window.location.href =
            "http://localhost:8080/web/src/pages/accounts.html";
        }
      });
    }, */

    /* cuando yo creo la cuenta el estatus es 201 y 200 cuando logueo*/

    signUp(event) {
      event.preventDefault();
      axios
        .post(
          "/api/clients",
          `firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`
        )
        .then(() => {
          this.signIn(event);
        })
        .then(() => {
          axios
            .post("/api/clients/current/accounts")
            .then((res) => console.log(res));
        })
        .catch(() => {
          this.mensajeError = "Sign up failed, check the information";
        });
    },
  },
}).mount("#app");
