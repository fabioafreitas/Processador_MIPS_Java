package br.ufrpe.deinfo.aoc.mips.example;

import java.io.IOException;

import br.ufrpe.deinfo.aoc.mips.MIPS;
import br.ufrpe.deinfo.aoc.mips.Simulator;
import br.ufrpe.deinfo.aoc.mips.State;
import jline.console.ConsoleReader;

public class MyMIPS implements MIPS{
	private static class OPCODE {
		public static final int addi = 0x8, addiu =0x9, andi= 0xc, beq = 0x4, bne =0x5, j = 0x2,
		jal = 0x3, lbu = 0x24, lhu = 0x25, ll = 0x30, lui = 0xf, lw = 0x23, ori = 0xd, slti = 0xa,
		sltiu = 0xb, sb = 0x28, sc = 0x38, sh = 0x29, sw = 0x2b, lwc1 = 0x31, ldc1 = 0x35,
		swc1 = 0x39, sdc1 = 0x3d, mfc0 = 0x10;
		private OPCODE() {}
	}
	private static class FUNCT {
		public static final int add = 0x20, addu = 0x21, and = 0x24, jr = 0x8, nor = 0x27,
		or = 0x25, slt = 0x2a, sltu = 0x2b, sll = 0x0, srl = 0x2, sub = 0x22, subu = 0x23,
		div = 0x1a, divu = 0x1b, mfhi = 0x10, mflo = 0x12, mfc0 = 0x0, mult = 0x18, multu = 0x19,
		sra = 0x3;
		private FUNCT() {}
	}
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
		
		
		int instrucaoAtual = state.readInstructionMemory(state.getPC()).intValue();
		executarInstrucao(state, instrucaoAtual);
		
		state.setPC(state.getPC()+4); // somando 4 bytes no valor do PC
	}
	
	private static int complementoa2_hw (int i) {
		i = 0xffff - i + 1;
		// desconsidera os 16 ultimos bits
		return i;
	}
	
	// comandos do tipo I
	public static void comandoI(State state, int opCode, int rs, int rt, int immediate) throws IOException{
		
		boolean negative = false;
		switch(opCode) {
			case OPCODE.addi: {
				// TODO addi
				// complemento a 2
				if (immediate >= 0b1000000000000000) {
					immediate = complementoa2_hw(immediate);
					negative = true;
				}
				int store = state.readRegister(rs) +
						(negative ? (-immediate) : immediate);
//				Simulator.info("immediate "+ Integer.toBinaryString(immediate));
//				Simulator.info("store "+ store);
				state.writeRegister(rt, state.readRegister(rs) +
						(negative ? (-immediate) : immediate));
				break;
			}
			case OPCODE.addiu: {
				// TODO addiu
				break;
			}
			case OPCODE.andi: {
				// TODO andi
				break;
			}
			case OPCODE.beq: {
				// TODO beq
				break;
			}
			case OPCODE.bne: {
				// TODO bne
				break;
			}
			case OPCODE.lbu: {
				// TODO lbu
				break;
			}
			case OPCODE.lhu: {
				// TODO lhu
				break;
			}
			case OPCODE.ll: {
				// TODO ll
				break;
			}
			case OPCODE.lui: {
				// TODO lui
				break;
			}
			case OPCODE.lw: {
				// TODO lw
				break;
			}
			case OPCODE.ori: {
				// TODO ori
				break;
			}
			case OPCODE.slti: {
				// TODO slti
				break;
			}
			case OPCODE.sltiu: {
				// TODO sltiu
				break;
			}
			case OPCODE.sb: {
				// TODO sb
				break;
			}
			case OPCODE.sc: {
				// TODO sc
				break;
			}
			case OPCODE.sh: {
				// TODO sh
				break;
			}
			case OPCODE.sw: {
				// TODO sw
				break;
			}
			case OPCODE.lwc1: {
				// TODO lwcl
				break;
			}
			case OPCODE.ldc1: {
				// TODO ldcl
				break;
			}
			case OPCODE.swc1: {
				// TODO swcl
				break;
			}
			case OPCODE.sdc1: {
				// TODO sdcl
				break;
			}
		}
	}
	
	// comandos do tipo R
	public static void comandoR(State state, int opCode, int rs, int rt, int rd, int shamt, int funct) throws IOException{
		
		if (opCode == 0x0) {
			switch (funct) {
				case FUNCT.add: {
					state.writeRegister(rd,
							(state.readRegister(rs) +
							state.readRegister(rt)));
					break;
				}
				case FUNCT.addu: {
					// TODO addu
					break;
				}
				case FUNCT.and: {
					// TODO and
					break;
				}
				case FUNCT.jr: {
					// TODO jr
					break;
				}
				case FUNCT.nor: {
					// TODO nor
					break;
				}
				case FUNCT.or: {
					// TODO or
					break;
				}
				case FUNCT.slt: {
					// TODO slt
					break;
				}
				case FUNCT.sltu: {
					// TODO sltu
					break;
				}
				case FUNCT.sll: {
					// TODO sll
					break;
				}
				case FUNCT.srl: {
					// TODO srl
					break;
				}
				case FUNCT.sub: {
					// TODO sub
					break;
				}
				case FUNCT.subu: {
					// TODO subu
					break;
				}
				case FUNCT.div: {
					// TODO div
					break;
				}
				case FUNCT.divu: {
					// TODO divu
					break;
				}
				case FUNCT.mfhi: {
					// TODO mfhi
					break;
				}
				case FUNCT.mflo: {
					// TODO mflo
					break;
				}
				case FUNCT.mult: {
					// TODO mult
					break;
				}
				case FUNCT.multu: {
					// TODO multu
					break;
				}
				case FUNCT.sra: {
					// TODO sra
					break;
				}
			}
		} else if (opCode == 0x10) {
			if (funct == FUNCT.mfc0) {
				// TODO mfc0
			}
		}
	}
	
	// comandos do tipo J
	public static void comandoJ(State state, int opCode, int address) throws IOException{
		if (opCode == OPCODE.j) {
			// TODO j
		} else {
			// TODO jal
		}
	}
	
	// Construtor da classe
	public MyMIPS() throws IOException {
		this.console = Simulator.getConsole();
	}
	public static void executarInstrucao (State state, int instrucao) throws IOException {
		// Formatacao
		int location_opcode = 26,
		location_rs = 21,
		location_rt = 16,
		location_rd = 11,
		location_shamt = 6;
		int opCode = instrucao >>> location_opcode;
		instrucao = instrucao - (opCode << location_opcode);
		if (opCode == 0x0 || opCode == OPCODE.mfc0) {
			// Tipo R
			int rs, rt, rd, shamt, funct;
			rs = instrucao >>> location_rs;
			instrucao = instrucao - (rs << location_rs);
			rt = instrucao >>> location_rt;
			instrucao = instrucao - (rt << location_rt);
			rd = instrucao >>> location_rd;
			instrucao = instrucao - (rd << location_rd);
			shamt = instrucao >>> location_shamt;
			funct = instrucao - (shamt << location_shamt);
			/*
			System.out.println("Tipo R\n"+ "opCode: "+ Integer.toBinaryString(opCode)
								+"\trs: "+ rs+"\trt: "+rt+"\trd: "+rd+"\tshamt: "+shamt+"\tfunct: "+ funct);
			for (Field f : FUNCT.class.getDeclaredFields()) {
				try {
				if (f.getInt(null) == funct) {
					System.out.println(f.getName());
					break;
				}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			*/
			comandoR(state, opCode, rs, rt, rd, shamt, funct);
			
		} else if (opCode == OPCODE.j || opCode == OPCODE.jal) {
			// Tipo J
			int address = instrucao;
			/*
			System.out.println("Tipo J\n"+"opCode: "+Integer.toHexString(opCode)+"\tadress: "+Integer.toHexString(address));
			for (Field f : OPCODE.class.getDeclaredFields()) {
				try {
				if (f.getInt(null) == opCode) {
					System.out.println(f.getName());
					break;
				}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			*/
			comandoJ(state, opCode, address);
		} else {
			// Tipo I
			int rs, rt, immediate;
			rs = instrucao >>> location_rs;
			instrucao = instrucao - (rs << location_rs);
			rt = instrucao >>> location_rt;
			immediate = instrucao - (rt << location_rt);
			/*
			System.out.println("Tipo I\n"+"opCode: "+opCode+"\trs: "+rs+"\trt: "+rt+"\timmediate: "+immediate);
			for (Field f : OPCODE.class.getDeclaredFields()) {
				try {
				if (f.getInt(null) == opCode) {
					System.out.println(f.getName());
					break;
				}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			*/
			comandoI(state, opCode, rs, rt, immediate);
		}
	}
	
	// Main que executará o console externo do simulador
	public static void main(String[] args) {
		/*
		int s[] = {0b00000001010010110100100000100000, 
				0b00100001010010010000000000000001, 
				0b00100101010010010000000000000001, 
				0b00000001010010110100100000100001, 
				0b00000001010010110100100000100100, 
				0b00110001010010010000000000000001, 
				0b00010001001010101111111111111001, 
				0b00010101001010101111111111111000, 
				0b00001000000100000000000000000000, 
				0b00001100000100000000000000000000, 
				0b00000001001000000000000000001000, 
				0b10010000000010010000000000000001, 
				0b10010100000010010000000000000001, 
				0b11000001010010010000000000000001, 
				0b00111100000010010000000000000001, 
				0b10001100000010010000000000000001, 
				0b00000001010010110100100000100111, 
				0b00000001010010110100100000100101, 
				0b00110101010010010000000000000001, 
				0b00000001010010110100100000101010, 
				0b00101001010010010000000000000001, 
				0b00101101010010010000000000000001, 
				0b00000001010010110100100000101011, 
				0b00000000000010100100100001000000, 
				0b00000000000010100100100001000010, 
				0b10100001010010010000000000000001, 
				0b11100001010010010000000000000001, 
				0b10100101010010010000000000000001, 
				0b10101101010010010000000000000001, 
				0b00000001010010110100100000100010, 
				0b00000001010010110100100000100011, 
				0b00010101011000000000000000000001, 
				0b00000000000000000000000000001101, 
				0b00000001010010110000000000011010, 
				0b00000000000000000100100000010010, 
				0b00010101011000000000000000000001, 
				0b00000000000000000000000000001101, 
				0b00000001010010110000000000011011, 
				0b00000000000000000100100000010010, 
				0b11000101010000010000000000000001, 
				0b11010101010000010000000000000001, 
				0b00000000000000000100100000010000, 
				0b00000000000000000100100000010010, 
				0b01000000000010010000100000000000, 
				0b00000001001010100000000000011000, 
				0b00000000000010100100100001000011, 
				0b11100101010000010000000000000001, 
				0b11110101010000010000000000000001};*/
		try {
			/*
			int j = 0;
			for (int i : s) {
				System.out.println(j+": "+Integer.toBinaryString(i));
				executarInstrucao(null, i);
				j++;
			}
			*/
			Simulator.setMIPS(new MyMIPS());
			Simulator.setLogLevel(Simulator.LogLevel.INFO);
			Simulator.start();
			
		} catch (Exception e) {		
			e.printStackTrace();
		}
	}
}
