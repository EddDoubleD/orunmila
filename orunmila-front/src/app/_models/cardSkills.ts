export interface CardSkills {
    header: string;
    skills: Array<SkillProgres>;
}

export interface SkillProgres {
    title: string;
    progress: number;
    color: string;
}