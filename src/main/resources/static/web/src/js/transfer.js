const { createApp } = Vue;

createApp({
  data() {
    return {
      nombre: "",
      accounts: [],
      amount: "",
      description: "",
      cuentaP: "",
      cuentaD: "",
      accountType: "miCuenta",
      miCuenta: "",
      otraCuenta: "",
      active: false,
    };
  },

  created() {
    axios.get("/api/clients/current").then((datos) => {
      this.accounts = datos.data.accountsDTO;
      this.nombre = datos.data.firstName;
    });
  },

  methods: {
    LogOut() {
      axios.post("/api/logout").then((response) => {
        window.location.href = "/web/src/index.html";
      });
    },
    clickMenuDos() {
      this.active = !this.active;
    },
    sendTransfer() {
      Swal.fire({
        title: "Estas seguro?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#014377",
        cancelButtonColor: "#ff0000",
        confirmButtonText: "Transferir",
      }).then((result) => {
        if (result.isConfirmed) {
          axios
            .post(
              `/api/transactions?amount=${this.amount}&description=${this.description}&originAccount=${this.cuentaP}&destinyAccount=${this.cuentaD}`
            )
            .then(() => {
              Swal.fire({
                title: "Exito!",
                text: "Su transacción fue creada!",
                icon: "success",
                timer: 2000,
              })
                .then(() => location.reload())
                .then(
                  (response) =>
                    (window.location.href = "/web/src/pages/accounts.html")
                );
            })

            .catch((error) => {
              Swal.fire({
                icon: "error",
                text: error.response.data,
              });
            });
        }
      });
    },
  },
}).mount("#app");
