package br.ufrpe.deinfo.aoc.mips.example;

import java.io.IOException;

import br.ufrpe.deinfo.aoc.mips.MIPS;
import br.ufrpe.deinfo.aoc.mips.Simulator;
import br.ufrpe.deinfo.aoc.mips.State;
import jline.console.ConsoleReader;

public class MyMIPS implements MIPS{
	
	// Atributo que representa o console externo, será manipulado no main
	@SuppressWarnings("unused")
	private ConsoleReader console;
	
	/* Este método executa uma instrução por vez
	 * 
	 * Recebe a instrução atual que está armazenada no registrador PC
	 * Program Counter. Esta instrução é manipulada de acordo com seu tipo: I, R, J.
	 * 
	 * Após a iteração da instrução atual o PC é incrementado em 4 Bytes, pois
	 * na arquitetura MIPS cada endereço de memória possui 4 Bytes, ou seja, 32
	 * bits.
	 */
	@Override
	public void execute(State state) throws Exception {
		/*  Se o Adress de PC for 0x00000000, então
		 *	alteramos o endereço de $gp com 0x00001800
		 *	e o endereço de $sp com 0x00003ffc.
		 *
		 *	CARACTERÍSTICAS DO MEMORY
		 *  CONFIGURATION DO MARS4_5
		 *	
		 *	precisamos alterar esses registradores
		 *  pois nesse simulador não possuimos 4gb
		 *  de memória.
		 *  
		 *  $gp é o registrador 28
		 *  $sp é o registrador 29
		 */
		if(state.getPC().equals(0)) {
			state.writeRegister(28, 0x1800);
			state.writeRegister(29, 0x3ffc);
		}
		
		String instrucaoAtual = Integer.toBinaryString(state.readInstructionMemory(state.getPC()));
		String opCode = instrucaoAtual.substring(0, 6);
		
		switch(opCode) {
		case "tipo i":
			
			break;
		case "tipo r":
			
			break;
		case "tipo j":
	
			break;
		}
		
		state.setPC(state.getPC()+4); // somando 4 bytes no valor do PC
	}
	
	// comandos do tipo I
	public static void comandoI(State state, String instrucaoAtual) throws IOException{
		
	}
	
	// comandos do tipo R
	public static void comandoR(State state, String instrucaoAtual) throws IOException{
		
	}
	
	// comandos do tipo J
	public static void comandoJ(State state, String instrucaoAtual) throws IOException{
		
	}
	
	// Método responsável por transformar todos os 32 bits da palavra binária
	// ,que inicialmente está no tipo Integer, numa string.
	// É necessário esse método pois sem ele ocorrerá perda de bits
	// e a operação atual ficará alterada
	public static String converterEmString(Integer palavra) {
		//	TODO 
		return "";
	}
	
	// Construtor da classe
	public MyMIPS() throws IOException {
		this.console = Simulator.getConsole();
	}
	
	// Main que executará o console externo do simulador
	public static void main(String[] args) {
		try {
			Simulator.setMIPS(new MyMIPS());
			Simulator.setLogLevel(Simulator.LogLevel.INFO);
			Simulator.start();
		} catch (Exception e) {		
			e.printStackTrace();
		}		
	}
}
