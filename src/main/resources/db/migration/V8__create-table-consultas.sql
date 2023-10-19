create table consultas(
id bigint not null AUTO_INCREMENT,
medico_id bigint not NULL,
paciente_id bigint not NULL,
data datetime not NULL,
primary key(id),
constraint fk_consultas_medico_id FOREIGN key(medico_id) references medicos(id),
constraint fk_consultas_paciente_id FOREIGN key(paciente_id) references pacientes(id)

);