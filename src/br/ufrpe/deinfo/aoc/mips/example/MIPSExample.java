/*------------------------------------------------------------------------------
 *
 *   UNIVERSIDADE FEDERAL RURAL DE PERNAMBUCO - UFRPE (www.ufrpe.br)
 *   DEPARTAMENTO DE ESTATÍSTICA E INFORMÁTICA - DEINFO (www.deinfo.ufrpe.br)
 *
 *------------------------------------------------------------------------------
 *   Exemplo de utilização do Simulador.
 *
 *   Copyright (C) 2015  André Aziz Camilo de Araujo (andreaziz@deinfo.ufrpe.br)
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>. 
 *------------------------------------------------------------------------------
 */
package br.ufrpe.deinfo.aoc.mips.example;

import java.io.IOException;

import jline.console.ConsoleReader;
import br.ufrpe.deinfo.aoc.mips.MIPS;
import br.ufrpe.deinfo.aoc.mips.Simulator;
import br.ufrpe.deinfo.aoc.mips.State;


public class MIPSExample implements MIPS {
	
	@SuppressWarnings("unused")
	private ConsoleReader console;
	
	public MIPSExample() throws IOException {
		this.console = Simulator.getConsole();
	}
	
	@Override
	public void execute(State state) throws Exception {
		
		Integer PC = state.getPC();
		Integer instructionCode = state.readInstructionMemory(PC);

		Simulator.debug("Instruction Code = " + instructionCode);
		
		state.setPC(state.getPC() + 4);				
	}
	
	public static void main(String[] args) {
		try {
			Simulator.setMIPS(new MIPSExample());
			Simulator.setLogLevel(Simulator.LogLevel.INFO);
			Simulator.start();
		} catch (Exception e) {		
			e.printStackTrace();
		}		
	}
}


