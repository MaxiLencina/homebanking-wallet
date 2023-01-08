Vue.createApp({
  data() {
    return {
      loanTypeId: 1,
      destinyAccountNumber: "",
      amount: 0,
      nombre: "",
      accounts: [],
      payments: "",
      loans: {},
      active: false,
    };
  },

  created() {
    axios.get("/api/clients/current").then((datos) => {
      this.accounts = datos.data.accountsDTO;
    }),
      axios.get("/api/loans").then((res) => {
        this.loans = res.data;
        this.loans = this.loans.sort((a, b) => a.id - b.id);
      });
  },

  methods: {
    LogOut() {
      axios.post("/api/logout").then((response) => {
        window.location.href = "/web/src/index.html";
      });
    },
    applyLoan() {
      Swal.fire({
        title: "Estas seguro?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#014377",
        cancelButtonColor: "#ff0000",
        confirmButtonText: "Prestamo aceptado",
      }).then((result) => {
        if (result.isConfirmed) {
          axios
            .post(
              "/api/loans",
              {
                loanId: this.loanTypeId,
                destinyAccountNumber: this.destinyAccountNumber,
                amount: this.amount,
                payments: this.payments,
              } //
            )

            .then(() => {
              Swal.fire({
                title: "Exito!",
                text: "Tu transacción a sido creada!",
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
              console.log(error.response.data);
              Swal.fire({
                icon: "error",
                text: error.response.data,
              });
            });
        }
      });
    },
    selecPayments() {
      let aux = [...this.loans];
      aux = aux.filter((loan) => this.loanTypeId == loan.id);
      return aux[0].payments;
    },

    clickMenuDos() {
      this.active = !this.active;
    },
  },
}).mount("#app");
