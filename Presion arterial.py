import tkinter as tk
from tkinter import messagebox, ttk


DIAS = ("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")


class AplicacionPresion:
    def __init__(self, ventana):
        self.ventana = ventana
        self.ventana.title("BPAnalyzer | Registro de presión arterial")
        self.ventana.geometry("920x570")
        self.ventana.minsize(760, 480)
        self.registros = {dia: {} for dia in DIAS}

        self._crear_estilos()
        self._crear_interfaz()

    def _crear_estilos(self):
        estilo = ttk.Style()
        estilo.theme_use("clam")
        estilo.configure("Titulo.TLabel", font=("Segoe UI", 20, "bold"), foreground="#12304a")
        estilo.configure("Subtitulo.TLabel", font=("Segoe UI", 10), foreground="#5c6b73")
        estilo.configure("Accent.TButton", font=("Segoe UI", 10, "bold"))

    def _crear_interfaz(self):
        contenedor = ttk.Frame(self.ventana, padding=24)
        contenedor.pack(fill="both", expand=True)
        contenedor.columnconfigure(1, weight=1)
        contenedor.rowconfigure(2, weight=1)

        ttk.Label(contenedor, text="Registro de presión arterial", style="Titulo.TLabel").grid(
            row=0, column=0, columnspan=2, sticky="w"
        )
        ttk.Label(
            contenedor,
            text="Añade mediciones y consulta todos los registros en una sola vista.",
            style="Subtitulo.TLabel",
        ).grid(row=1, column=0, columnspan=2, sticky="w", pady=(4, 18))

        formulario = ttk.LabelFrame(contenedor, text="Nueva medición", padding=16)
        formulario.grid(row=2, column=0, sticky="ns", padx=(0, 18))

        self.dia = tk.StringVar(value=DIAS[0])
        self.nombre = tk.StringVar()
        self.peso = tk.StringVar()
        self.edad = tk.StringVar()
        self.sistolica = tk.StringVar()
        self.diastolica = tk.StringVar()

        campos = (
            ("Día", self.dia, "combo"),
            ("Nombre", self.nombre, "entry"),
            ("Peso (kg)", self.peso, "entry"),
            ("Edad", self.edad, "entry"),
            ("Sistólica (mmHg)", self.sistolica, "entry"),
            ("Diastólica (mmHg)", self.diastolica, "entry"),
        )
        for fila, (etiqueta, variable, tipo) in enumerate(campos):
            ttk.Label(formulario, text=etiqueta).grid(row=fila, column=0, sticky="w", pady=6)
            if tipo == "combo":
                control = ttk.Combobox(formulario, textvariable=variable, values=DIAS, state="readonly", width=18)
            else:
                control = ttk.Entry(formulario, textvariable=variable, width=21)
            control.grid(row=fila, column=1, sticky="ew", padx=(12, 0), pady=6)

        ttk.Button(formulario, text="Agregar medición", style="Accent.TButton", command=self.agregar_registro).grid(
            row=len(campos), column=0, columnspan=2, sticky="ew", pady=(16, 6)
        )
        ttk.Button(formulario, text="Limpiar campos", command=self.limpiar_campos).grid(
            row=len(campos) + 1, column=0, columnspan=2, sticky="ew"
        )

        panel = ttk.Frame(contenedor)
        panel.grid(row=2, column=1, sticky="nsew")
        panel.columnconfigure(0, weight=1)
        panel.rowconfigure(1, weight=1)
        ttk.Label(panel, text="Mediciones registradas", font=("Segoe UI", 12, "bold")).grid(
            row=0, column=0, sticky="w", pady=(0, 8)
        )

        columnas = ("dia", "nombre", "peso", "edad", "presion")
        self.tabla = ttk.Treeview(panel, columns=columnas, show="headings", selectmode="browse")
        encabezados = {"dia": "Día", "nombre": "Nombre", "peso": "Peso", "edad": "Edad", "presion": "Presión"}
        anchos = {"dia": 90, "nombre": 160, "peso": 80, "edad": 70, "presion": 120}
        for columna in columnas:
            self.tabla.heading(columna, text=encabezados[columna])
            self.tabla.column(columna, width=anchos[columna], anchor="center")
        self.tabla.grid(row=1, column=0, sticky="nsew")
        barra = ttk.Scrollbar(panel, orient="vertical", command=self.tabla.yview)
        barra.grid(row=1, column=1, sticky="ns")
        self.tabla.configure(yscrollcommand=barra.set)

        self.estado = tk.StringVar(value="Todavía no hay mediciones registradas.")
        ttk.Label(panel, textvariable=self.estado, style="Subtitulo.TLabel").grid(row=2, column=0, sticky="w", pady=(10, 0))

    def agregar_registro(self):
        try:
            nombre = self.nombre.get().strip()
            peso = float(self.peso.get())
            edad = int(self.edad.get())
            sistolica = int(self.sistolica.get())
            diastolica = int(self.diastolica.get())
            if not nombre or peso <= 0 or edad <= 0 or sistolica <= 0 or diastolica <= 0:
                raise ValueError
        except ValueError:
            messagebox.showerror("Datos inválidos", "Completa todos los campos con valores positivos.")
            return

        dia = self.dia.get()
        datos = {"peso": peso, "edad": edad, "presion": {"sistolica": sistolica, "diastolica": diastolica}}
        self.registros[dia][nombre] = datos
        self._actualizar_tabla()
        self.limpiar_campos()
        self.estado.set(f"{sum(len(personas) for personas in self.registros.values())} medición(es) registrada(s).")

    def _actualizar_tabla(self):
        for elemento in self.tabla.get_children():
            self.tabla.delete(elemento)
        for dia, personas in self.registros.items():
            for nombre, datos in personas.items():
                presion = datos["presion"]
                self.tabla.insert("", "end", values=(
                    dia, nombre, f"{datos['peso']:.1f} kg", f"{datos['edad']} años",
                    f"{presion['sistolica']}/{presion['diastolica']} mmHg",
                ))

    def limpiar_campos(self):
        self.nombre.set("")
        self.peso.set("")
        self.edad.set("")
        self.sistolica.set("")
        self.diastolica.set("")


def iniciar_aplicacion():
    ventana = tk.Tk()
    AplicacionPresion(ventana)
    ventana.mainloop()


if __name__ == "__main__":
    iniciar_aplicacion()
