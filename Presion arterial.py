def registrar_presion():
    registros = {"Lunes": {}, "Martes": {}, "Miércoles": {}, "Jueves": {}, "Viernes": {}, "Sábado": {}, "Domingo": {}}
    
    while True:
        dia = input("Ingrese el día de la semana (Lunes a Domingo): ").capitalize()
        if dia not in registros:
            print("Día no válido. Intente nuevamente.")
            continue
        
        nombre = input("Ingrese el nombre de la persona: ")
        peso = float(input("Ingrese el peso en kg: "))
        edad = int(input("Ingrese la edad: "))
        sistolica = int(input("Ingrese la presión sistólica: "))
        diastolica = int(input("Ingrese la presión diastólica: "))
        
        registros[dia][nombre] = {
            "peso": peso,
            "edad": edad,
            "presion": {
                "sistolica": sistolica,
                "diastolica": diastolica
            }
        }
        
        continuar = input("¿Desea agregar otra persona? (s/n): ").lower()
        if continuar != 's':
            break
    
    return registros

# Ejecutar el programa y mostrar los registros
datos_presion = registrar_presion()
print("\nRegistros de presión arterial:")
for dia, personas in datos_presion.items():
    if personas:
        print(f"\n{dia}:")
        for nombre, datos in personas.items():
            print(f"  Nombre: {nombre}, Peso: {datos['peso']} kg, Edad: {datos['edad']} años, "
                  f"Presión: {datos['presion']['sistolica']}/{datos['presion']['diastolica']} mmHg")
