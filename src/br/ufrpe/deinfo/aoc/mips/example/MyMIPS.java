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
	// Atributo que representa o console externo, ser� manipulado no main
	@SuppressWarnings("unused")
	private ConsoleReader console;
	
	/* Este m�todo executa uma instru��o por vez
	 * 
	 * Recebe a instru��o atual que est� armazenada no registrador PC
	 * Program Counter. Esta instru��o � manipulada de acordo com seu tipo: I, R, J.
	 * 
	 * Ap�s a itera��o da instru��o atual o PC � incrementado em 4 Bytes, pois
	 * na arquitetura MIPS cada endere�o de mem�ria possui 4 Bytes, ou seja, 32
	 * bits.
	 */
	@Override
	public void execute(State state) throws Exception {
		/*  Se o Adress de PC for 0x00000000, ent�o
		 *	alteramos o endere�o de $gp com 0x00001800
		 *	e o endere�o de $sp com 0x00003ffc.
		 *
		 *	CARACTER�STICAS DO MEMORY
		 *  CONFIGURATION DO MARS4_5
		 *	
		 *	precisamos alterar esses registradores
		 *  pois nesse simulador n�o possuimos 4gb
		 *  de mem�ria.
		 *  
		 *  $gp � o registrador 28
		 *  $sp � o registrador 29
		 */
		if(state.getPC().equals(0)) {
			state.writeRegister(28, 0x1800);
			state.writeRegister(29, 0x3ffc);
		}
		
		int instrucaoAtual = state.readInstructionMemory(state.getPC()).intValue();
		executarInstrucao(state, instrucaoAtual);
		state.setPC(state.getPC()+4); // somando 4 bytes no valor do PC
	}
	
	// comandos do tipo I
	public static void comandoI(State state, int opCode, int rs, int rt, int immediate) throws IOException{
		if (immediate >= 0b1000000000000000) {
			
			// converte de complemento a 2 16 bit para 32 bit
			immediate = immediate + 0xffff0000;
		}
		switch(opCode) {
			case OPCODE.addi: { // R[rt] = R[rs] + SignExtImm
				state.writeRegister(rt, ((int) state.readRegister(rs)) + immediate);
				// Overflow ?
				break;
			}
			case OPCODE.addiu: { // R[rt] = R[rs] + SignExtImm
				state.writeRegister(rt, ((int) state.readRegister(rs)) + immediate);
				break;
			}
			case OPCODE.andi: { // R[rt] = R[rs] & ZeroExtImm
				state.writeRegister(rt, ((int) state.readRegister(rs)) & immediate);
				break;
			}
			case OPCODE.beq: { //if(R[rs]==R[rt]) faca->  PC=PC+4+BranchAddr
				int branchaddr = immediate << 2;
				if(state.readRegister(rs).intValue() == state.readRegister(rt).intValue()){				
					state.setPC(state.getPC()+4+branchaddr);
				}
				else{
					state.setPC(state.getPC()+4);
				}
				break;
			}
			case OPCODE.bne: { //if(R[rs]!=R[rt]) faca->  PC=PC+4+BranchAddr
				int branchaddr = immediate << 2;
				if( state.readRegister(rs).intValue() != state.readRegister(rt).intValue() ){
					state.setPC(state.getPC()+4+branchaddr);
				}
				else{
					state.setPC(state.getPC()+4);
				}
				break;
			}
			case OPCODE.lbu: { // R[rt]={24�b0,M[R[rs]+SignExtImm](7:0)}
				// TODO n�o sei se o unsigned muda em algo, N�O SEI SE EST� CERTO
				Integer address = state.readWordDataMemory(state.readRegister(rs)+immediate);
				Integer halfWord = 0x000000ff & address;
				state.writeRegister(rt, halfWord);
				break;
			}
			case OPCODE.lhu: { // R[rt]={16�b0,M[R[rs] +SignExtImm](15:0)}
				// TODO n�o sei se o unsigned muda em algo, N�O SEI SE EST� CERTO
				Integer address = state.readWordDataMemory(state.readRegister(rs)+immediate);
				Integer halfWord = 0x0000ffff & address;
				state.writeRegister(rt, halfWord);
				break;
			}
			case OPCODE.ll: { // N�O � PRA FAZER
				break;        // N�O � PRA FAZER  
			}
			case OPCODE.lui: { // R[rt] = {imm, 16�b0}
				state.writeRegister(rt, immediate<<16);
				break;
			}
			case OPCODE.lw: { // R[rt] = M[R[rs]+SignExtImm]
				// N�O SEI SE EST� CERTO
				state.writeRegister(rt, state.readWordDataMemory(state.readRegister(rs)+immediate));
				break;
			}
			case OPCODE.ori: { // R[rt] = R[rs] | ZeroExtImm
				state.writeRegister(rt, ((int) state.readRegister(rs)) | immediate );
				break;
			}
			case OPCODE.slti: { // R[rt] = (R[rs] < SignExtImm)? 1 : 0
				state.writeRegister(rt, ( ((int) state.readRegister(rs) ) < immediate)? 1 : 0 );
				break;
			}
			case OPCODE.sltiu: { // R[rt] = (R[rs] < SignExtImm)? 1 : 0
				state.writeRegister(rt, Integer.compareUnsigned( state.readRegister(rs), immediate) == -1 ? 1 : 0 );
				break;
			}
			case OPCODE.sb: { // M[R[rs]+SignExtImm](7:0) = R[rt](7:0)
				Integer byteWord = 0x000000ff & state.readRegister(rt);
				state.writeByteDataMemory(state.readRegister(rs)+immediate, byteWord);
				break;
			}
			case OPCODE.sc: { // N�O � PRA FAZER
				break;        // N�O � PRA FAZER
			}
			case OPCODE.sh: { // M[R[rs]+SignExtImm](15:0) = R[rt](15:0)
				Integer halfWord = 0x0000ffff & state.readRegister(rt);
				state.writeHalfwordDataMemory(state.readRegister(rs)+immediate, halfWord);
				break;
			}
			case OPCODE.sw: { // M[R[rs]+SignExtImm] = R[rt]
				state.writeWordDataMemory(state.readRegister(rs)+immediate, state.readRegister(rt));
				break;
			}
			case OPCODE.lwc1: {
				// TODO lwcl  UTILIZA O COPROCESSOR 1 D�VIDA
				break;
			}
			case OPCODE.ldc1: {
				// TODO ldcl  UTILIZA O COPROCESSOR 1 D�VIDA
				break;
			}
			case OPCODE.swc1: {
				// TODO swcl  UTILIZA O COPROCESSOR 1 D�VIDA
				break;
			}
			case OPCODE.sdc1: {
				// TODO sdcl  UTILIZA O COPROCESSOR 1 D�VIDA
				break;
			}
		}
	}
	
	// comandos do tipo R
	public static void comandoR(State state, int opCode, int rs, int rt, int rd, int shamt, int funct) throws IOException{
		
		if (opCode == 0x0) {
			switch (funct) {
				case FUNCT.add: { // R[rd] = R[rs] + R[rt]
					state.writeRegister(rd, (state.readRegister(rs) + state.readRegister(rt)));
					break;
				}
				case FUNCT.addu: { // R[rd] = R[rs] + R[rt] pose ser que seja de outro jeito
					state.writeRegister(rd, (state.readRegister(rs) + state.readRegister(rt)));
					break;
				}
				case FUNCT.and: { // R[rd] = R[rs] & R[rt]
					state.writeRegister(rd, (state.readRegister(rs) & state.readRegister(rt)) );
					break;
				}
				case FUNCT.jr: { // PC=R[rs]
					state.setPC(state.readRegister(rs));
					break;
				}
				case FUNCT.nor: { // R[rd] = ~(R[rs] | R[rt])
					state.writeRegister(rd, ~( state.readRegister(rs) | state.readRegister(rt)) );
					break;
				}
				case FUNCT.or: { // R[rd] = R[rs] | R[rt]
					state.writeRegister(rd, ( state.readRegister(rs) | state.readRegister(rt)) );
					break;
				}
				case FUNCT.slt: { // R[rd] = (R[rs] < R[rt]) ? 1 : 0
					state.writeRegister(rd, ( ( (int) state.readRegister(rs) ) 
													< ( (int) state.readRegister(rt) ) ? 1 : 0 ) );
					break;
				}
				case FUNCT.sltu: { 
					state.writeRegister(rd, Integer.compareUnsigned( state.readRegister(rs),
							state.readRegister(rt)) == -1 ? 1 : 0 );
					break;
				}
				case FUNCT.sll: { // R[rd] = R[rt] << shamt
					state.writeRegister(rd,  state.readRegister(rt) << shamt );
					break;
				}
				case FUNCT.srl: { // R[rd] = R[rt] >> shamt
					state.writeRegister(rd,  state.readRegister(rt) >> shamt );
					break;
				}
				case FUNCT.sub: { // R[rd] = R[rs] - R[rt]
					state.writeRegister(rd, state.readRegister(rs) - state.readRegister(rt));
					break;
				}
				case FUNCT.subu: {
					state.writeRegister(rd, state.readRegister(rs) - state.readRegister(rt));
					break;
				}
				case FUNCT.div: {
					// TODO div   UTILIZA OS REGISTRADORES HI E LO D�VIDA
					break;
				}
				case FUNCT.divu: {
					// TODO divu   UTILIZA OS REGISTRADORES HI E LO D�VIDA
					break;
				}
				case FUNCT.mfhi: {
					// TODO mfhi   UTILIZA OS REGISTRADORES HI E LO D�VIDA
					break;
				}
				case FUNCT.mflo: {
					// TODO mflo   UTILIZA OS REGISTRADORES HI E LO D�VIDA
					break;
				}
				case FUNCT.mult: {
					// TODO mult   UTILIZA OS REGISTRADORES HI E LO D�VIDA
					break;
				}
				case FUNCT.multu: {
					// TODO multu   UTILIZA OS REGISTRADORES HI E LO D�VIDA
					break;
				}
				case FUNCT.sra: { // R[rd] = R[rt] >>> shamt
					state.writeRegister(rd, state.readRegister(rt) >>> shamt);
					break;
				}
			}
		} else if (opCode == 0x10) {
			if (funct == FUNCT.mfc0) {
				// TODO mfc0 UTILIZA O COPRESSADOR 0 D�VIDA
			}
		}
	}
	
	// comandos do tipo J
	public static void comandoJ(State state, int opCode, int address) throws IOException{
		if (opCode == OPCODE.j) {
			state.setPC(address);
		} else {
			state.writeRegister(31, state.getPC()+4); //talvez seja +8 por causa do branch delay slot ou nao
			state.setPC(address);
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
			comandoR(state, opCode, rs, rt, rd, shamt, funct);
			
		} else if (opCode == OPCODE.j || opCode == OPCODE.jal) {
			// Tipo J
			int address = instrucao;
			comandoJ(state, opCode, address);
		} else {
			// Tipo I
			int rs, rt, immediate;
			rs = instrucao >>> location_rs;
			instrucao = instrucao - (rs << location_rs);
			rt = instrucao >>> location_rt;
			immediate = instrucao - (rt << location_rt);
			comandoI(state, opCode, rs, rt, immediate);
		}
	}
	
	// Main que executar� o console externo do simulador
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
